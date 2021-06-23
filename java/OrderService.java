package com.sec.srm.order.purchaser.service;

import com.alibaba.fastjson.JSONObject;
import com.sec.srm.core.web.rest.errors.BusinessException;
import com.sec.srm.order.common.domain.Order;
import com.sec.srm.order.common.domain.*;
import com.sec.srm.order.common.domain.enumeration.*;
import com.sec.srm.order.common.repository.MaterialDocItemRepository;
import com.sec.srm.order.common.repository.PocDictionaryRepository;
import com.sec.srm.order.common.repository.SupplierRepository;
import com.sec.srm.order.external.dto.*;
import com.sec.srm.order.external.service.SourcingInterfaceService;
import com.sec.srm.order.purchaser.repository.DepartmentRepository;
import com.sec.srm.order.purchaser.repository.OrderItemRepository;
import com.sec.srm.order.purchaser.repository.OrderRepository;
import com.sec.srm.order.purchaser.repository.PurchaserCodeRepository;
import com.sec.srm.order.purchaser.service.dto.OrderItemDTO;
import com.sec.srm.order.purchaser.service.dto.*;
import com.sec.srm.order.purchaser.service.mapper.OrderMapper;
import com.sec.srm.order.purchaser.web.rest.vm.OrderAuditVM;
import com.sec.srm.order.purchaser.web.rest.vm.OrderStatsVM;
import com.sec.srm.order.purchaser.web.rest.vm.OrderUploadSearchVM;
import com.sec.srm.order.purchaser.web.rest.vm.OrderVM;
import com.sec.srm.order.sap.domain.TaxMapping;
import com.sec.srm.order.sap.repository.TaxMappingRepository;
import com.sec.srm.order.sysuser.service.SysUserRelationService;
import com.sec.srm.order.util.DateUtils;
import com.sec.srm.order.util.Filter;
import com.sec.srm.order.util.QueryParams;
import com.sec.srm.security.security.SecurityUtils;
import com.sec.srm.security.security.SrmAuthUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author liuxiaojie
 */
@Service("PurchaserOrderService")
@Transactional
public class OrderService {
    private final Logger log = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private PurchaserCodeRepository purchaserCodeRepository;

//    @Autowired
//    private OrderViewRepository orderViewRepository;

    @Autowired
    private PocDictionaryRepository pocDictionaryRepository;

    @Autowired
    private PurchaserService purchaserService;

    @Autowired
    private OrderAuditService orderAuditService;

    @Autowired
    private MaterialDocService materialDocService;

    @Autowired
    private MaterialDocItemRepository materialDocItemRepository;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private SourcingInterfaceService sourcingInterfaceService;

    @Autowired
    private ContractInterfaceService contractInterfaceService;
    
    @Autowired
    private SysUserRelationService sysUserRelationService;

    @Autowired
    private TaxMappingRepository taxMappingRepository;
    /**
     * 获取采购方的订单统计数据
     *
     * @return 订单统计数据
     */
    @Transactional(readOnly = true)
    public OrderStatisticsDTO getOrderStatistics(List<String> purchaserCompCodes) {

        OrderStatisticsDTO orderStatisticsDTO = new OrderStatisticsDTO();

        /*List<Object[]> counts = orderRepository.countOrderStatusByPurchaserCodes(
                purchaserService.getAllPurchaserCodes(purchaserCompCodes));

        long notReceivedOrderCount = 0;
        long partialReceivedOrderCount = 0;
        long deliveryOrderCount = 0;
        long allOrderCount = 0;

        if (counts != null && counts.size() > 0) {
            for (Object[] count : counts) {
                if ("1000000001".equals(count[0])) {
                    notReceivedOrderCount += (long) count[1];
                } else if ("1000000002".equals(count[0])) {
                    partialReceivedOrderCount += (long) count[1];
                    deliveryOrderCount += (long) count[1];
                } else if ("1000000003".equals(count[0])) {
                    deliveryOrderCount += (long) count[1];
                }

                allOrderCount += (long) count[1];
            }
        }*/

//        orderStatisticsDTO.setNotReceivedOrderCount(notReceivedOrderCount);
//        orderStatisticsDTO.setPartialReceivedOrderCount(partialReceivedOrderCount);
//        orderStatisticsDTO.setDeliveryOrderCount(deliveryOrderCount);
//        orderStatisticsDTO.setAllOrderCount(allOrderCount);

        return orderStatisticsDTO;
    }

    /**
     * 获取采购方的所有订单
     * @param pageable
     * @param orderVM
     * @param isPartialOutSourcing
     * @return
     */
    @Transactional(readOnly = true)
    public Page<OrderDTO> findAll(Pageable pageable,   OrderVM orderVM, boolean isPartialOutSourcing) {

        // 当前用户拥有权限的公司Ids
        Optional<UserDetails>  user = SecurityUtils.getCurrentUser();
        SrmAuthUser srmAuthUser = (SrmAuthUser)  user.get();
        String companyCode = srmAuthUser.getCompanyCode();
        String deptCode = srmAuthUser.getDeptCode();
        String userCode = srmAuthUser.getUsername();
        
        List<String> supplierCodes = sysUserRelationService.findSupplierByUserCode(userCode);

        log.debug("user: {}, companyCode: {}, detpCode: {}",srmAuthUser.getName(),companyCode,deptCode);
        log.info("userCode: {}", userCode);

        QueryParams<Order> queryParams = new QueryParams<>();

        queryParams.setDistinct(true);

        // 公司和部门
        queryParams.and(new Filter("purchaser.purchaserCode.code", Filter.Operator.eq, companyCode));

        queryParams.and(new Filter("deptCode", Filter.Operator.eq, deptCode));
        
        if (supplierCodes!=null && !supplierCodes.isEmpty()) {
        	queryParams.and(new Filter("supplier.id", Filter.Operator.in, supplierCodes));
        }
        
        // 供应商名称条件查询
        if (StringUtils.isNotBlank(orderVM.getSupplierName())) {
            queryParams.and(new Filter("supplier.enterprise.name", Filter.Operator.like, orderVM.getSupplierName().trim()));
        }

        // 订单编号条件查询
        if (StringUtils.isNotBlank(orderVM.getOrderNum())) {
            queryParams.and(new Filter("orderNum", Filter.Operator.like, orderVM.getOrderNum().trim()));
        }

        if(StringUtils.isNotBlank(orderVM.getOrderType())){
            queryParams.and(new Filter("orderType", Filter.Operator.like, orderVM.getOrderType().trim()));
        }
        if(StringUtils.isNotBlank(orderVM.getOrderDateFrom()) && StringUtils.isNotBlank(orderVM.getOrderDateTo())){
            Date from = DateUtils.parseDate(orderVM.getOrderDateFrom(), DateUtils.PATTERN_DATE);
            Date to = DateUtils.parseDate(orderVM.getOrderDateTo()+ DateUtils.END_DATE_TIME , DateUtils.PATTERN_DATEALLTIME);
            queryParams.and(new Filter("orderDate", Filter.Operator.between, Arrays.asList(from, to)));
        }

        /*if(StringUtils.isNotBlank(orderVM.getPlannedDeliveryDateFrom()) && StringUtils.isNotBlank(orderVM.getPlannedDeliveryDateTo())){
            Date from = DateUtils.parseDate(orderVM.getPlannedDeliveryDateFrom(), DateUtils.PATTERN_DATE);
            Date to = DateUtils.parseDate(orderVM.getPlannedDeliveryDateTo() + DateUtils.END_DATE_TIME , DateUtils.PATTERN_DATEALLTIME);
            queryParams.and(new Filter("plannedDeliveryDate", Filter.Operator.between, Arrays.asList(from, to)));
        }*/

        // 订单状态条件查询
        if (orderVM.getOrderStatus() != null && orderVM.getOrderStatus() .size() > 0) {

            queryParams.and(new Filter("orderExtra.orderStatus", Filter.Operator.in, orderVM.getOrderStatus() ));
        }
        
        if (orderVM.getPurchaseType() != null) {
        	queryParams.and(new Filter("purchaseType", Filter.Operator.eq, orderVM.getPurchaseType()));
        }



        if(isPartialOutSourcing) {
            queryParams.and(new Filter("purchaseType", Filter.Operator.eq, PurchaseTypeEnum.PARTIAL_OUTSOURCING));
        }else {
            queryParams.and(new Filter("purchaseType", Filter.Operator.ne, PurchaseTypeEnum.PARTIAL_OUTSOURCING));
        }

        Specification<Order> specification = Specification.where(queryParams);

        //投产批次
        if(StringUtils.isNotBlank(orderVM.getOperationBatch()) || (StringUtils.isNotBlank(orderVM.getPlannedDeliveryDateFrom()) && StringUtils.isNotBlank(orderVM.getPlannedDeliveryDateTo()))){
            Specification<Order> itemSpecification= new Specification<Order>(){
                @Override
                public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Join<Order, OrderItem> join = root.join("orderItems");
                    Predicate predicate = criteriaBuilder.conjunction();
                    if(StringUtils.isNotBlank(orderVM.getOperationBatch())){
                        predicate.getExpressions().add(criteriaBuilder.like(join.get("operationBatch"), "%"+orderVM.getOperationBatch().trim()+"%"));
                    }

                    if(StringUtils.isNotBlank(orderVM.getPlannedDeliveryDateFrom()) && StringUtils.isNotBlank(orderVM.getPlannedDeliveryDateTo())){
                        Date from = DateUtils.parseDate(orderVM.getPlannedDeliveryDateFrom(), DateUtils.PATTERN_DATE);
                        Date to = DateUtils.parseDate(orderVM.getPlannedDeliveryDateTo() + DateUtils.END_DATE_TIME , DateUtils.PATTERN_DATEALLTIME);
                        predicate.getExpressions().add(criteriaBuilder.between(join.get("deliveryDate"), from, to));
                    }
                    return predicate;
                }
            };
            specification = specification.and(itemSpecification);

        }

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                pageable.getSort().and(new Sort(Direction.DESC, "orderNum")));

        Page<Order> orders = orderRepository.findAll(specification, pageable);

        return orders.map(orderMapper::toDTOList);
    }

    @Transactional(readOnly = true)
    public List<OrderReportDTO> findByIds(List<String> ids) {
        Optional<UserDetails>  user = SecurityUtils.getCurrentUser();
        SrmAuthUser srmAuthUser = (SrmAuthUser)  user.get();
        String companyCode = srmAuthUser.getCompanyCode();
        String deptCode = srmAuthUser.getDeptCode();
        List<Order> orders = orderRepository.findByIdInAndAndDeptCode(ids, deptCode);

       return orders.stream().map(orderMapper::toDTOList).map(OrderReportDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderReportDTO> findOrderItemsByIds(List<String> ids) {
        Optional<UserDetails>  user = SecurityUtils.getCurrentUser();
        SrmAuthUser srmAuthUser = (SrmAuthUser)  user.get();
        String companyCode = srmAuthUser.getCompanyCode();
        String deptCode = srmAuthUser.getDeptCode();
        List<OrderItem> orders = orderItemRepository.findAllByIdInOrderByOrderNumDescOrderItemNumAsc(ids);
        return orders.stream().map(orderMapper::toItemDTO).map(OrderReportDTO::new).collect(Collectors.toList());
    }

    /**
     * 根据id获取订单
     *
     * @param id 订单id
     * @return 订单数据
     */
    @Transactional(readOnly = true)
    public Optional<OrderDTO> findById(String id) {

        Optional<Order> order = orderRepository.findById(id);

        Optional<OrderDTO> orderDTOOptional =  order.map(orderMapper::toDTOList);

        if(orderDTOOptional.isPresent()){

            OrderDTO orderDTO = orderDTOOptional.get();
            
            String deptCode = SecurityUtils.getCurrentUserDeptCode().orElse("");
            if(!deptCode.equals(orderDTO.getDeptCode())){
                throw new AccessDeniedException("cannot to view other department's order");
            }
            Department department = departmentRepository.findOneByCmpCodeAndDeptCode(orderDTO.getRefPurchaserCode(), orderDTO.getDeptCode());
            orderDTO.setDeptName(department.getDeptName());
        }

        return orderDTOOptional;
    }
    
    @Transactional(readOnly = true)
    public Page<OrderItemDTO> findOrderItemsByOrderId(String id, Pageable pageable) {
    	pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),  pageable.getSort().and(new Sort(Direction.ASC, "orderItemNum")));
    	Page<OrderItem> items = orderItemRepository.findByOrderIdAndIsDeleted(id,false,pageable);
    	return items.map(OrderItemDTO::new);
    }

    /**
     * 所有供应商名称
     * @return 供应商名称
     */
    @Transactional(readOnly = true)
    public List<SupplierDTO> getSupplierName( ) {
       // List<String> purchaserIds = new ArrayList<>();
//        List<SupplierDTO> supplierDTOList = new ArrayList<>();
        //根据采购方code，查询采购方id
//        List<PurchaserCode> purchaserCodeList = purchaserCodeRepository.findByCodeIn(
//                purchaserService.getAllPurchaserCodes(purchaserCodes));
//        for (PurchaserCode purchaserCode : purchaserCodeList) {
//            purchaserIds.add(purchaserCode.getPurchaser().getId());
//        }
//        // 查询供应商id，并拼接
//        if (purchaserIds.size() > 0) {
//            List<Order> orderList = orderRepository.findByPurchaserIdIn(purchaserIds);
//            if (orderList != null && orderList.size() > 0) {
//                List<String> compCodeId = new ArrayList<>();
//                for (Order supplierId : orderList) {
//                    compCodeId.add(supplierId.getSupplier().getId());
//                }
//                // 查询供应商信息
//                List<Supplier> supplierList = supplierRepository.findAllById(compCodeId);
//                supplierDTOList = supplierList.stream().map(SupplierDTO::new).collect(Collectors.toList());
//            }
//            return supplierDTOList;
//        }
        return supplierRepository.findAll().stream().map(SupplierDTO::new).filter(supplierDTO ->  supplierDTO.getSupplierName()!=null ).collect(Collectors.toList());
    }

    /**
     * 修改订单收货数量
     *
     * @param order 订单信息
     */
    public void updateGoodsReceiveNum(Order order) {

        List<OrderItem> orderItems = order.getOrderItems();
        if (orderItems == null || orderItems.size() == 0) {
            String message = messageSource.getMessage("p.order.item.not.found", null, LocaleContextHolder.getLocale());
            throw new BusinessException(message);
        }

        //全部订单行收货完成
        Boolean all_gr = true;

        for (OrderItem orderItem : orderItems) {
            //计算订单明细总数量
            BigDecimal grQty = orderItem.getGrItems().stream()
                    .map(MaterialDocItem::getQty)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            if (grQty != null && grQty.compareTo(BigDecimal.ZERO) > 0) {
                if (!"X".equalsIgnoreCase(orderItem.getNomoregr()) && grQty.compareTo(orderItem.getOrderQty()) >= 0) {
                    //修改NO_MORE_GR标识
                    orderItem.setNomoregr("X");
        }
            }
            if (!"X".equalsIgnoreCase(orderItem.getNomoregr())) {
                all_gr = false;
            }
        }

        PocDictionary pocDictionary = new PocDictionary(
                all_gr ? GoodsReceiveStatusEnum.ALL_GR.getId() : GoodsReceiveStatusEnum.PARTIAL_GR.getId());
       // order.setGrStatus(pocDictionary);

        orderRepository.save(order);

    }

    public Order updateByMaterialDocFromSap(Order order) {

        List<OrderItem> orderItems = order.getOrderItems();
        if (orderItems == null || orderItems.size() == 0) {
            return order;
        }

        List<MaterialDocItem> mdItemList = materialDocService.getMaterialDocItemsFromSap(order);

        if (mdItemList == null || mdItemList.size() == 0) {
            return order;
        }

//        String orderGrStatus = order.getGrStatus() == null ? "" : order.getGrStatus().getId();
//        String orderQcStatus = order.getQcStatus() == null ? "" : order.getQcStatus().getId();
        boolean orderChanged = false;
        boolean all_gr = true;
        boolean all_qc = true;

        for (OrderItem orderItem : orderItems) {
            // 有新的收货信息
            boolean orderItemChanged = false;

            List<MaterialDocItem> mdItems = mdItemList.stream()
                    .filter(item -> item.getOrderItemNum() == orderItem.getOrderItemNum()).collect(Collectors.toList());

            BigDecimal gr_qty = mdItems.stream()
                    .filter(item -> "101".equals(item.getMoveType()) || "107".equals(item.getMoveType()))
                    .map(MaterialDocItem::getQty)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal gr_reverse_qty = mdItems.stream()
                    .filter(item -> "102".equals(item.getMoveType()) || "108".equals(item.getMoveType()))
                    .map(MaterialDocItem::getQty)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal grQty = gr_qty.subtract(gr_reverse_qty);

            /*if (grQty.compareTo(BigDecimal.ZERO) > 0) {
                if (!GoodsReceiveStatusEnum.PARTIAL_GR.getId().equals(orderGrStatus)) {
                    order.setGrStatus(new PocDictionary(GoodsReceiveStatusEnum.PARTIAL_GR.getId()));
                    orderChanged = true;
                }

                if (!"X".equalsIgnoreCase(orderItem.getNomoregr()) && grQty.compareTo(orderItem.getOrderQty()) >= 0) {
                    //修改NO_MORE_GR标识
                    orderItem.setNomoregr("X");
                    orderItemChanged = true;
                }
            }*/

            if (!"X".equalsIgnoreCase(orderItem.getNomoregr()) && grQty.compareTo(orderItem.getOrderQty()) < 0) {
                all_gr = false;
            }

            BigDecimal qc_qty = mdItems.stream()
                    .filter(item -> "109".equals(item.getMoveType()) || "321".equals(item.getMoveType()))
                    .map(MaterialDocItem::getQty)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal qc_reverse_qty = mdItems.stream()
                    .filter(item -> "110".equals(item.getMoveType()) || "322".equals(item.getMoveType()))
                    .map(MaterialDocItem::getQty)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal qcQty = qc_qty.subtract(qc_reverse_qty);

            if (qcQty.compareTo(BigDecimal.ZERO) > 0) {
//                if (!QualityCheckStatusEnum.PARTIAL_QC.getId().equals(orderGrStatus)) {
//                    order.setQcStatus(new PocDictionary(QualityCheckStatusEnum.PARTIAL_QC.getId()));
//                    orderChanged = true;
//                }
            }

            if (qcQty.compareTo(orderItem.getOrderQty()) < 0) {
                all_qc = false;
            }

            if (orderItemChanged) {
                orderItemRepository.save(orderItem);
            }
        }

//        if (all_gr && !GoodsReceiveStatusEnum.ALL_GR.getId().equals(orderGrStatus)) {
//           // order.setGrStatus(new PocDictionary(GoodsReceiveStatusEnum.ALL_GR.getId()));
//            orderChanged = true;
//        }
//
//        if (all_gr && all_qc && !QualityCheckStatusEnum.ALL_QC.getId().equals(orderQcStatus)) {
//           // order.setQcStatus(new PocDictionary(QualityCheckStatusEnum.ALL_QC.getId()));
//            orderChanged = true;
//        }

        if (orderChanged) {
            return orderRepository.save(order);
        } else {
            return order;
        }
    }

    /**
     * 订单审批修改状态
     *
     * @param orderAuditVM 订单审批信息
     */
    public OrderDTO audit(OrderAuditVM orderAuditVM) {
        //查询订单数据
        Order order = orderRepository.findById(orderAuditVM.getId()).orElseThrow(() -> new BusinessException(
                messageSource.getMessage("p.order.not.found", null, LocaleContextHolder.getLocale())));

        //审批状态为待审批
//        String auditStatus = order.getOrderStatus() == null ? "" : order.getOrderStatus().getId();
//        if (!OrderStatusEnum.NOT_AUDITED.getId().equals(auditStatus)) {
//            String message = messageSource.getMessage("p.order.audit.operated.error", null, LocaleContextHolder.getLocale());
//            throw new BusinessException(message);
//        }

//        OperationLog operationLog = new OperationLog();
//        switch (orderAuditVM.getAuditStatus()) {
//            case Constants.CON_STR_1:
//                order.setOrderStatus(pocDictionaryRepository.getOne(OrderStatusEnum.NOT_RECEIVED.getId()));
//                operationLog.setOperationResult(Constants.AUDIT_APPROVE);
//                break;
//            case Constants.CON_STR_2:
//                order.setOrderStatus(pocDictionaryRepository.getOne(OrderStatusEnum.AUDITED_REJECTED.getId()));
//                operationLog.setOperationResult(Constants.AUDIT_REFUSE);
//                break;
//            default:
//                throw new IllegalStateException("Unexpected value: " + orderAuditVM.getAuditStatus());
//        }

        order.setOrderMemo(orderAuditVM.getAuditRemake());

        // 调用采购订单审批接口
        StringBuffer errorInfo = new StringBuffer();
        boolean auditResult = orderAuditService.auditOrder(order, errorInfo);

        if (!auditResult) {
            throw new BusinessException(errorInfo.toString());
        }

        order = orderRepository.save(order);

        //保存操作日志
//        operationLog.setObjectType("order");
//        operationLog.setObjectId(order.getId());
//        operationLog.setOperationType("审批");
//        operationLog.setOperationDesc(orderAuditVM.getAuditRemake());
//        operationLogService.saveOperationService(operationLog);

        return orderMapper.toDtoForDetail(order);
    }


    @Transactional(readOnly = true)
    public Page<OrderItemDTO> findAllOrderItems(Pageable pageable,  OrderVM orderVM, boolean isPartialOutSourcing) {

        // 当前用户拥有权限的公司Ids
        Optional<UserDetails>  user = SecurityUtils.getCurrentUser();
        SrmAuthUser srmAuthUser = (SrmAuthUser)  user.get();
        String companyCode = srmAuthUser.getCompanyCode();
        String deptCode = srmAuthUser.getDeptCode();
        String userCode = srmAuthUser.getUsername();

        List<String> supplierCodes = sysUserRelationService.findSupplierByUserCode(userCode);

         QueryParams<OrderItem> queryParams = new QueryParams<>();
         // 公司和部门

        queryParams.and(new Filter("order.deptCode", Filter.Operator.eq, deptCode));

        queryParams.and(new Filter("order.purchaser.purchaserCode.code", Filter.Operator.eq, companyCode));

        queryParams.and(new Filter("isDeleted", Filter.Operator.ne, true));
        
        if (supplierCodes!=null && !supplierCodes.isEmpty()) {
        	queryParams.and(new Filter("order.supplier.id", Filter.Operator.in, supplierCodes));
        }

        if(StringUtils.isNotBlank(orderVM.getOrderNum()) ){
            queryParams.and(new Filter("order.orderNum", Filter.Operator.like, orderVM.getOrderNum().trim()));
        }

        if(StringUtils.isNotBlank(orderVM.getOrderItemNum()) ){
            queryParams.and(new Filter("orderItemNum", Filter.Operator.eq, orderVM.getOrderItemNum().trim()));
        }

        if(StringUtils.isNotBlank(orderVM.getOrderDateFrom()) && StringUtils.isNotBlank(orderVM.getOrderDateTo())){
            Date from = DateUtils.parseDate(orderVM.getOrderDateFrom(), DateUtils.PATTERN_DATE);
            Date to = DateUtils.parseDate(orderVM.getOrderDateTo() + DateUtils.END_DATE_TIME , DateUtils.PATTERN_DATEALLTIME);
            queryParams.and(new Filter("order.orderDate", Filter.Operator.between, Arrays.asList(from, to)));
        }

        if(StringUtils.isNotBlank(orderVM.getDeptName()) ){
            queryParams.and(new Filter("order.deptCode", Filter.Operator.like, orderVM.getDeptName().trim()));
        }

        if(StringUtils.isNotBlank(orderVM.getPurchasGrp()) ){
            queryParams.and(new Filter("order.purchaseGrp", Filter.Operator.like, orderVM.getPurchasGrp().trim()));
        }

        // 供应商名称条件查询
        if (StringUtils.isNotBlank(orderVM.getSupplierName())) {
            queryParams.and(new Filter("order.supplier.enterprise.name", Filter.Operator.like, orderVM.getSupplierName().trim()));
        }

        // 供应商代码条件查询
        if (StringUtils.isNotBlank(orderVM.getSupplierCode())) {
            //queryParams.and(new Filter("order.supplier.enterprise.code", Filter.Operator.like, orderVM.getSupplierCode().trim()));
        	queryParams.and(new Filter("order.supplier.supplierCode.compcodeCd", Filter.Operator.like, orderVM.getSupplierCode().trim()));
        }

        // 物料编码条件查询
        if (StringUtils.isNotBlank(orderVM.getMaterialCode())) {
            queryParams.and(new Filter("materialCode", Filter.Operator.like, orderVM.getMaterialCode().trim()));
        }

        // 物料名称条件查询
        if (StringUtils.isNotBlank(orderVM.getMaterialText())) {
            queryParams.and(new Filter("materialText", Filter.Operator.like, orderVM.getMaterialText().trim()));
        }


        // 计划交货日期条件查询
        if(StringUtils.isNotBlank(orderVM.getPlannedDeliveryDateFrom()) && StringUtils.isNotBlank(orderVM.getPlannedDeliveryDateTo())){
            Date from = DateUtils.parseDate(orderVM.getPlannedDeliveryDateFrom(), DateUtils.PATTERN_DATE);
            Date to = DateUtils.parseDate(orderVM.getPlannedDeliveryDateTo() + DateUtils.END_DATE_TIME , DateUtils.PATTERN_DATEALLTIME);
            queryParams.and(new Filter("deliveryDate", Filter.Operator.between, Arrays.asList(from, to)));
        }

        // 制单人条件查询
        if (StringUtils.isNotBlank(orderVM.getOwnerBy())) {
            queryParams.and(new Filter("order.ownerBy", Filter.Operator.like, orderVM.getOwnerBy().trim()));
        }

        // 投产批次条件查询
        if (StringUtils.isNotBlank(orderVM.getOperationBatch())) {
            queryParams.and(new Filter("operationBatch", Filter.Operator.like, orderVM.getOperationBatch().trim()));
        }

        // 生产订单号
        if ( StringUtils.isNotBlank(orderVM.getRefOrderNum())) {
            queryParams.and(new Filter("refOrderNum", Filter.Operator.like, orderVM.getRefOrderNum().trim()));
        }

        // 采购申请号
        if ( StringUtils.isNotBlank(orderVM.getPurchaseReqNo())) {
            queryParams.and(new Filter("prNum", Filter.Operator.like, orderVM.getPurchaseReqNo().trim()));
        }

        // 图号
        if ( StringUtils.isNotBlank(orderVM.getBlueprintNO())) {
            queryParams.and(new Filter("blueprintNO", Filter.Operator.like, orderVM.getBlueprintNO().trim()));
        }
        
        // 质检状态
        if (orderVM.getQcStatus() !=null) {
        	queryParams.and(new Filter("orderItemExtendView.qcStatus", Filter.Operator.eq, orderVM.getQcStatus()));
        }

        // 订单状态条件查询
        if (orderVM.getOrderStatus() != null && orderVM.getOrderStatus() .size() > 0) {
        	
        	if(isPartialOutSourcing) {
        		
        		if(orderVM.getOrderStatus().contains(OrderStatusEnum.NOT_PRICE_APPRAISAL.getCode())) {
            		List<String> newList = new ArrayList<String>();
            		newList.addAll(orderVM.getOrderStatus());
            		newList.remove(OrderStatusEnum.NOT_PRICE_APPRAISAL.getCode());
            		queryParams.and(new Filter("unitPrice", Filter.Operator.eq, 0));
            		
            		if(newList.size()>0) {
            			queryParams.and(new Filter("orderItemExtendView.status", Filter.Operator.in, newList ));
            		}
            	}else {
            		queryParams.and(new Filter("orderItemExtendView.status", Filter.Operator.in, orderVM.getOrderStatus() ));
            	}
            }else
            {
//            	if(orderVM.getOrderStatus().contains(OrderStatusEnum.NOT_PRICE_APPRAISAL.getCode()) || 
//            			orderVM.getOrderStatus().contains(OrderStatusEnum.PRICE_APPRAISAL.getCode())) {
//            		List<String> newList = new ArrayList<String>();
//            		newList.addAll(orderVM.getOrderStatus());
//            		newList.remove(OrderStatusEnum.NOT_PRICE_APPRAISAL.getCode());
//            		newList.remove(OrderStatusEnum.PRICE_APPRAISAL.getCode());
//            		
//            		if(orderVM.getOrderStatus().contains(OrderStatusEnum.NOT_PRICE_APPRAISAL.getCode()) ) {
//            			queryParams.and(new Filter("orderItemExtendView.inquiryHdCd", Filter.Operator.isNull,null));
//            		}else if(orderVM.getOrderStatus().contains(OrderStatusEnum.PRICE_APPRAISAL.getCode()) ) {
//            			queryParams.and(new Filter("orderItemExtendView.inquiryHdCd", Filter.Operator.isNotNull,null ));
//            		}
//            		
//            		if(newList.size()>0) {
//            			queryParams.and(new Filter("orderItemExtendView.status", Filter.Operator.in, newList ));
//            		}
//            	}else {
//            		queryParams.and(new Filter("orderItemExtendView.status", Filter.Operator.in, orderVM.getOrderStatus() ));
//            	}
            	
            	List<String> newList = new ArrayList<String>();
        		newList.addAll(orderVM.getOrderStatus());
        		
        		if(newList.contains(OrderStatusEnum.NOT_PRICE_APPRAISAL.getCode()) ) {
        			queryParams.and(new Filter("orderItemExtendView.inquiryHdCd", Filter.Operator.isNull,null));
        			newList.remove(OrderStatusEnum.NOT_PRICE_APPRAISAL.getCode());
        		}else if(newList.contains(OrderStatusEnum.PRICE_APPRAISAL.getCode()) ) {
        			queryParams.and(new Filter("orderItemExtendView.inquiryHdCd", Filter.Operator.isNotNull,null ));
        			newList.remove(OrderStatusEnum.PRICE_APPRAISAL.getCode());
        		}
        		
        		List<String> inquiryStatusList = new ArrayList<String>();
        		if (newList.contains(InquiryStatusEnum.QUOTE_WAITING.getCode())) {
        			inquiryStatusList.add(InquiryStatusEnum.QUOTE_WAITING.getCode());
        			newList.remove(InquiryStatusEnum.QUOTE_WAITING.getCode());
        		}
        		if (newList.contains(InquiryStatusEnum.DECISION_WAITING.getCode())) {
        			inquiryStatusList.add(InquiryStatusEnum.DECISION_WAITING.getCode());
        			newList.remove(InquiryStatusEnum.DECISION_WAITING.getCode());
        		}
        		if (newList.contains(InquiryStatusEnum.DECISION_DONE.getCode())) {
        			inquiryStatusList.add(InquiryStatusEnum.DECISION_DONE.getCode());
        			newList.remove(InquiryStatusEnum.DECISION_DONE.getCode());
        		}
        		if (newList.contains(InquiryStatusEnum.QUOTE_FAILURE.getCode())) {
        			inquiryStatusList.add(InquiryStatusEnum.QUOTE_FAILURE.getCode());
        			newList.remove(InquiryStatusEnum.QUOTE_FAILURE.getCode());
        		}
        		if (newList.contains(InquiryStatusEnum.PRICE_AUDIT_WAITING.getCode())) {
        			inquiryStatusList.add(InquiryStatusEnum.PRICE_AUDIT_WAITING.getCode());
        			newList.remove(InquiryStatusEnum.PRICE_AUDIT_WAITING.getCode());
        		}
        		if (newList.contains(InquiryStatusEnum.PRICE_AUDIT_REJECT.getCode())) {
        			inquiryStatusList.add(InquiryStatusEnum.PRICE_AUDIT_REJECT.getCode());
        			newList.remove(InquiryStatusEnum.PRICE_AUDIT_REJECT.getCode());
        		}
        		if (newList.contains(InquiryStatusEnum.PRICE_AUDIT_DONE.getCode())) {
        			inquiryStatusList.add(InquiryStatusEnum.PRICE_AUDIT_DONE.getCode());
        			newList.remove(InquiryStatusEnum.PRICE_AUDIT_DONE.getCode());
            	}
        		if(inquiryStatusList.size()>0) {
        			queryParams.and(new Filter("orderItemExtendView.inquiryStatus", Filter.Operator.in, inquiryStatusList ));
        		}
        		
        		if(newList.size()>0) {
        			queryParams.and(new Filter("orderItemExtendView.status", Filter.Operator.in, newList ));
        		}
        		
            }
        	
            
        }

        if(isPartialOutSourcing) {
            queryParams.and(new Filter("order.purchaseType", Filter.Operator.eq, PurchaseTypeEnum.PARTIAL_OUTSOURCING));
        }else {
            queryParams.and(new Filter("order.purchaseType", Filter.Operator.ne, PurchaseTypeEnum.PARTIAL_OUTSOURCING));
        }

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                pageable.getSort().and(new Sort(Direction.DESC, "orderNum"))
                        .and(new Sort(Direction.ASC, "orderItemNum"))
                        .and(new Sort(Direction.DESC, "lastModifiedDate")));
        
        if(!isPartialOutSourcing){
        	if (orderVM.getOrderStatus() != null && orderVM.getOrderStatus().size() > 0 &&
            		( orderVM.getOrderStatus().contains(OrderStatusEnum.NOT_PRICE_APPRAISAL.getCode()) ||
            		 orderVM.getOrderStatus().contains(OrderStatusEnum.PRICE_APPRAISAL.getCode()))
            		) {
            	Specification<OrderItem> oneYuan = new Specification<OrderItem>() {
                	
        			private static final long serialVersionUID = 104483544126723136L;

        			@Override
        			public Predicate toPredicate(Root<OrderItem> root, CriteriaQuery<?> query,
        					CriteriaBuilder criteriaBuilder) {
        				Predicate restrictions = criteriaBuilder.conjunction();
        				restrictions.getExpressions().add(criteriaBuilder.equal(criteriaBuilder.quot(root.get("unitPrice"), root.get("unit")),
        						criteriaBuilder.literal(1.0d)));
        				return criteriaBuilder.and(queryParams.apllySearchConditions(root, criteriaBuilder, restrictions));
        				
        			}
                };
                Page<OrderItem> ordersItems = orderItemRepository.findAll(oneYuan, pageable);

                return ordersItems.map(orderMapper::toItemDTO);
                
            }
        }
       
       Page<OrderItem> ordersItems = orderItemRepository.findAll(queryParams, pageable);

       return ordersItems.map(orderMapper::toItemDTO);

    }

    @Transactional(readOnly = true)
    public Page<OrderItemDTO> findAllOrderItemsByUpload(Pageable pageable,  OrderUploadSearchVM uploadSearchVM) {


        // 当前用户拥有权限的公司Ids
        Optional<UserDetails>  user = SecurityUtils.getCurrentUser();
        SrmAuthUser srmAuthUser = (SrmAuthUser)  user.get();
        String companyCode = srmAuthUser.getCompanyCode();
        String deptCode = srmAuthUser.getDeptCode();
        String userCode = srmAuthUser.getUsername();

        List<String> supplierCodes = sysUserRelationService.findSupplierByUserCode(userCode);

        QueryParams<OrderItem> queryParams = new QueryParams<>();
        // 公司和部门

        queryParams.and(new Filter("order.deptCode", Filter.Operator.eq, deptCode));

        queryParams.and(new Filter("order.purchaser.purchaserCode.code", Filter.Operator.eq, companyCode));

        if (supplierCodes!=null && !supplierCodes.isEmpty()) {
        	queryParams.and(new Filter("order.supplier.id", Filter.Operator.in, supplierCodes));
        }

        if(uploadSearchVM.isSearchNormal()){
            queryParams.and(new Filter("order.purchaseType", Filter.Operator.ne, PurchaseTypeEnum.PARTIAL_OUTSOURCING));
        }else {
            queryParams.and(new Filter("order.purchaseType", Filter.Operator.eq, PurchaseTypeEnum.PARTIAL_OUTSOURCING));
        }

        Specification specification =  new Specification<OrderItem>() {
            @Override
            public Predicate toPredicate(Root<OrderItem> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                Predicate restrictions = cb.conjunction();

                List<Predicate> list = new ArrayList<>();
                for ( OrderVM orderVm:  uploadSearchVM.getSearchRows()) {
                    Predicate orderNumPredicate =  cb.equal(root.get("orderNum"), orderVm.getOrderNum().trim());
                    Predicate orderItemNumPredicate = cb.equal(root.get("orderItemNum"), orderVm.getOrderItemNum().trim());

                    if(StringUtils.isNotBlank(orderVm.getMaterialCode())){
                        Predicate materialCodePredictae = cb.equal(root.get("materialCode"), orderVm.getMaterialCode().trim());
                        Predicate items =cb.and(orderNumPredicate, orderItemNumPredicate, materialCodePredictae);
                       list.add(items);

                     }else {
                         list.add(cb.and(orderNumPredicate, orderItemNumPredicate));
                    }
                }
                Predicate [] predicates = new Predicate[list.size()];
                restrictions.getExpressions().add( cb.or(list.toArray(predicates) ));



                return cb.and(queryParams.apllySearchConditions(root, cb, restrictions));
            }
        };

        int page = 0;
        int size = 20;
        if(uploadSearchVM.getSize()==0) {
        	page = pageable.getPageNumber();
        	size = pageable.getPageSize();
        }else {
        	page = uploadSearchVM.getPage();
        	size = uploadSearchVM.getSize();
        }
        pageable = PageRequest.of(page, size,
        		pageable.getSort().and(new Sort(Direction.DESC, "lastModifiedDate")));





        Page<OrderItem> ordersItems = orderItemRepository.findAll(specification, pageable);

        return ordersItems.map(orderMapper::toItemDTO);

    }

    public void updateOrderItemQualityStatus (String orderNum, Integer orderItemNum, String status) throws Exception {
       OrderItem orderItem =  orderItemRepository.findByOrderNumAndOrderItemNum(  orderNum,   orderItemNum);
       if(orderItem != null ) {
           orderItem.getOrderItemExtendView().setQcStatus(QualityCheckStatusEnum.convertQualitySystemStatus(status));
           orderItemRepository.save(orderItem);
       }else {
           throw new Exception ("订单行不存在");
       }
    }
    
    public void updateOrderItemQualityStatus4Delivery (String orderNum, Integer orderItemNum) throws Exception {
    	log.info("Update order item quality status when print paint receipt, orderNum:{}, orderItemNum:{}", orderNum, orderItemNum);
        OrderItem orderItem =  orderItemRepository.findByOrderNumAndOrderItemNum(  orderNum,   orderItemNum);
        if (orderItem != null) {
        	if (orderItem.getOrderItemExtendView() == null) return;
        	log.info("Update order item quality status when print paint receipt, qcStatus:{}", orderItem.getOrderItemExtendView().getQcStatus());
        	if ( QualityCheckStatusEnum.PARTIAL_PAINTING.equals(orderItem.getOrderItemExtendView().getQcStatus()) 
        				|| QualityCheckStatusEnum.PO_OUT.equals(orderItem.getOrderItemExtendView().getQcStatus()) ) {
	            orderItem.getOrderItemExtendView().setQcStatus(QualityCheckStatusEnum.PAINTING);
	            orderItemRepository.save(orderItem);
        	}
        } else {
            throw new Exception ("订单行不存在");
        }
     }


    
    public void updateQcStatus(String orderNum, int orderItemNum) {
        List<MaterialDocItem> materialDocItems = materialDocItemRepository.findByOrderNumAndOrderItemNum(orderNum, orderItemNum);

        OrderItem orderItem = orderItemRepository.findByOrderNumAndOrderItemNum(orderNum, orderItemNum);
        if(orderItem == null) {
            log.warn("no order item found with orderNum={} and orderItemNum={}", orderNum, orderItemNum);
            return;
        }
        BigDecimal totalQualifiedNum = BigDecimal.ZERO;
        BigDecimal totalConcessionsNumber = BigDecimal.ZERO;
        BigDecimal totalUnqualifiedNumber = BigDecimal.ZERO;
        for (MaterialDocItem materialDocItem: materialDocItems) {
            if(materialDocItem.getQualifiedNum() != null) {
                totalQualifiedNum = totalQualifiedNum.add(materialDocItem.getQualifiedNum());
            }
            if(materialDocItem.getConcessionsNumber() != null) {
                totalConcessionsNumber = totalConcessionsNumber.add(materialDocItem.getConcessionsNumber());
            }
            if(materialDocItem.getUnqualifiedNumber() != null) {
                totalUnqualifiedNumber = totalUnqualifiedNumber.add(materialDocItem.getUnqualifiedNumber());
            }
        }
        OrderItemExtend orderItemExtend = orderItem.getOrderItemExtendView();
        orderItemExtend.setQualifiedNum(totalQualifiedNum);
        orderItemExtend.setUnqualifiedNum(totalUnqualifiedNumber);
        orderItemExtend.setConcessionsNum(totalConcessionsNumber);

    }

    public List<SupplierDTO> findSupplier(String shortName) {

        List<Supplier> suppliers = supplierRepository.findAllByEnterpriseShortNameLikeIgnoreCase("%"+shortName+"%");

        return suppliers.stream().map(SupplierDTO::new).collect(Collectors.toList());
    }

    public Page<OrderStatsDTO> findAOrderStatBySupplier(Pageable pageable, OrderStatsVM orderStatsVM){
       Page<Supplier> suppliers = supplierRepository.findAllByEnterpriseShortNameLikeIgnoreCase("%"+orderStatsVM.getSupplierShortName()+"%", pageable);

       return suppliers.map(supplier ->  getOrderStatusDataForSupplier(supplier, orderStatsVM, null));
    }

    public List<OrderStatsDTO> findAOrderStatBySupplier(OrderStatsVM orderStatsVM){
        List<Supplier> suppliers = supplierRepository.findAllById(orderStatsVM.getSelectedIds());


        return  IntStream.range(0, suppliers.size())
                .mapToObj(i -> getOrderStatusDataForSupplier(suppliers.get(i),orderStatsVM, i+1))
                .collect(Collectors.toList());
    }


    private OrderStatsDTO getOrderStatusDataForSupplier(Supplier supplier, OrderStatsVM orderStatsVM, Integer idx) {
        Instant orderDateFrom = null, orderDateTo = null;
        OrderStatsDTO orderStatDTO = new OrderStatsDTO();
        if(supplier.getSupplierCode()!=null) {
            orderStatDTO.setSupplierCode(supplier.getSupplierCode().getCompcodeCd());
        }
        orderStatDTO.setIndex(idx);
        orderStatDTO.setSupplierId(supplier.getId());
        orderStatDTO.setSupplierShortName(supplier.getEnterprise().getShortName());
        if("year".equals(orderStatsVM.getDateRangeType())){
            orderStatDTO.setDateRange(orderStatsVM.getYear());
            orderDateFrom = DateUtils.parseDate(orderStatsVM.getYear()+"-01-01", DateUtils.PATTERN_DATE).toInstant();
            orderDateTo = DateUtils.parseDate(orderStatsVM.getYear()+"-12-31 23:59:59", DateUtils.PATTERN_DATEALLTIME).toInstant();
            //当前
            OrderStatsDataDTO data = this.getStatData(orderStatsVM.getDateRangeType(), supplier, orderDateFrom, orderDateTo);
            orderStatDTO.setOrderQty(data.getOrderQty());
            orderStatDTO.setOrderAmt(data.getOrderAmt());
            orderStatDTO.setPartCategoryQty(data.getPartCategoryQty());
            orderStatDTO.setContractAmt(data.getContractAmt());
            orderStatDTO.setPaymentAmt(data.getPaymentAmt());

            //同比和环比都为上一年
            int lastYear = Integer.parseInt(orderStatsVM.getYear())-1;
            Instant orderDateFromHb = DateUtils.parseDate(lastYear +"-01-01", DateUtils.PATTERN_DATE).toInstant();
            Instant orderDateToHb = DateUtils.parseDate(lastYear +"-12-31 23:59:59", DateUtils.PATTERN_DATEALLTIME).toInstant();
            OrderStatsDataDTO dataHb = this.getStatData(orderStatsVM.getDateRangeType(), supplier, orderDateFromHb, orderDateToHb);


            orderStatDTO.setOrderAmtHb(dataHb.getOrderAmt());
            orderStatDTO.setPartCategoryQtyHb(BigDecimal.valueOf(data.getPartCategoryQty() ==0 ? 0: dataHb.getPartCategoryQty()/data.getPartCategoryQty()));
            orderStatDTO.setContractAmtHb(dataHb.getContractAmt());
            orderStatDTO.setPaymentAmtHb(dataHb.getPaymentAmt());

            orderStatDTO.setOrderAmtTb(dataHb.getOrderAmt());
            orderStatDTO.setPartCategoryQtyTb(BigDecimal.valueOf(data.getPartCategoryQty() ==0 ? 0: dataHb.getPartCategoryQty()/data.getPartCategoryQty()));
            orderStatDTO.setContractAmtTb(dataHb.getContractAmt());
            orderStatDTO.setPaymentAmtTb(dataHb.getPaymentAmt());


        }else if("quarter".equals(orderStatsVM.getDateRangeType())){
            orderStatDTO.setDateRange(orderStatsVM.getYear() +" 第"+orderStatsVM.getQuarter() +"季度");

            //当前
            Instant [] current = this.quarterToInstant(orderStatsVM.getYear(), orderStatsVM.getQuarter());
            OrderStatsDataDTO data = this.getStatData(orderStatsVM.getDateRangeType(), supplier, current[0], current[1]);
            orderStatDTO.setOrderQty(data.getOrderQty());
            orderStatDTO.setOrderAmt(data.getOrderAmt());
            orderStatDTO.setPartCategoryQty(data.getPartCategoryQty());
            orderStatDTO.setContractAmt(data.getContractAmt());
            orderStatDTO.setPaymentAmt(data.getPaymentAmt());

            //同比
            int lastYear = Integer.parseInt(orderStatsVM.getYear())-1;
            Instant [] lastYearDate = this.quarterToInstant(lastYear+"", orderStatsVM.getQuarter());
            OrderStatsDataDTO dataTb = this.getStatData(orderStatsVM.getDateRangeType(), supplier, lastYearDate[0], lastYearDate[1]);
            orderStatDTO.setOrderAmtTb(dataTb.getOrderAmt());
            orderStatDTO.setPartCategoryQtyTb(BigDecimal.valueOf(data.getPartCategoryQty()==0?0:dataTb.getPartCategoryQty()/data.getPartCategoryQty()));
            orderStatDTO.setContractAmtTb(dataTb.getContractAmt());
            orderStatDTO.setPaymentAmtTb(dataTb.getPaymentAmt());

            //环比
            OrderStatsDataDTO dataHb;
            if("1".equals(orderStatsVM.getQuarter())){
                int lastYearHb = Integer.parseInt(orderStatsVM.getYear())-1;
                Instant [] lastYearDateHb = this.quarterToInstant(lastYear+"", "4");
                dataHb = this.getStatData(orderStatsVM.getDateRangeType(), supplier, lastYearDateHb[0], lastYearDateHb[1]);
            }else {
                int preQuarter = Integer.parseInt(orderStatsVM.getQuarter())-1;
                Instant [] lastYearDateHb = this.quarterToInstant(orderStatsVM.getYear(), preQuarter+"");
                dataHb = this.getStatData(orderStatsVM.getDateRangeType(), supplier, lastYearDateHb[0], lastYearDateHb[1]);
            }
            orderStatDTO.setOrderAmtHb(dataHb.getOrderAmt());
            orderStatDTO.setPartCategoryQtyHb(BigDecimal.valueOf(data.getPartCategoryQty() ==0 ? 0: dataHb.getPartCategoryQty()/data.getPartCategoryQty()));
            orderStatDTO.setContractAmtHb(dataHb.getContractAmt());
            orderStatDTO.setPaymentAmtHb(dataHb.getPaymentAmt());

        }else {
            //month range..
            orderStatDTO.setDateRange(orderStatsVM.getMonthFrom()+"~" + orderStatsVM.getMonthTo());
            orderDateFrom = DateUtils.parseDate(orderStatsVM.getMonthFrom(), DateUtils.PATTERN_YEARMOTH).toInstant();

            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(DateUtils.parseDate(orderStatsVM.getMonthTo(), DateUtils.PATTERN_YEARMOTH));
            calendar.set(Calendar.MONTH,  calendar.get(Calendar.MONTH)+1);
            orderDateTo =   calendar.toInstant(). minus(1, ChronoUnit.MILLIS) ;

            //当前
            OrderStatsDataDTO data = this.getStatData(orderStatsVM.getDateRangeType(), supplier, orderDateFrom, orderDateTo);
            orderStatDTO.setOrderQty(data.getOrderQty());
            orderStatDTO.setOrderAmt(data.getOrderAmt());
            orderStatDTO.setPartCategoryQty(data.getPartCategoryQty());
            orderStatDTO.setContractAmt(data.getContractAmt());
            orderStatDTO.setPaymentAmt(data.getPaymentAmt());

            //同比
            String [] dateFromArry = orderStatsVM.getMonthFrom().split("\\-");
            String [] dateToArry = orderStatsVM.getMonthTo().split("\\-");

            int fromYear = Integer.parseInt(dateFromArry[0]);
            int fromMonth = Integer.parseInt((dateFromArry[1]));

            int toYear =Integer.parseInt(dateToArry[0]);
            int toMonth=Integer.parseInt(dateToArry[1]);


            Instant orderDateTbFrom = DateUtils.parseDate((fromYear-1)+"-" + fromMonth, DateUtils.PATTERN_YEARMOTH).toInstant();

            GregorianCalendar calendarMonthend = new GregorianCalendar();
            calendarMonthend.setTime(DateUtils.parseDate((toYear-1)+"-" +toMonth, DateUtils.PATTERN_YEARMOTH));
            calendarMonthend.set(Calendar.MONTH,  calendar.get(Calendar.MONTH));
            Instant orderDateTbTo =   calendarMonthend.toInstant(). minus(1, ChronoUnit.MILLIS) ;


            OrderStatsDataDTO dataTb = this.getStatData(orderStatsVM.getDateRangeType(), supplier, orderDateTbFrom, orderDateTbTo);

            orderStatDTO.setOrderAmtTb(dataTb.getOrderAmt());
            orderStatDTO.setPartCategoryQtyTb(BigDecimal.valueOf(data.getPartCategoryQty()==0?0:dataTb.getPartCategoryQty()/data.getPartCategoryQty()));
            orderStatDTO.setContractAmtTb(dataTb.getContractAmt());
            orderStatDTO.setPaymentAmtTb(dataTb.getPaymentAmt());

            //环比
            int totalMonthes = (toYear-fromYear) * 12 + toMonth- fromMonth +1;
            GregorianCalendar calendarHb= new GregorianCalendar();
            calendarHb.setTime(DateUtils.parseDate(orderStatsVM.getMonthFrom(), DateUtils.PATTERN_YEARMOTH));
            Instant orderDateHbTo = calendarHb.toInstant(). minus(1, ChronoUnit.MILLIS) ;
            calendarHb.set(Calendar.MONTH,  calendar.get(Calendar.MONTH)- 2* totalMonthes);
            Instant orderDateHbFrom = calendarHb.toInstant();


            OrderStatsDataDTO dataHb = this.getStatData(orderStatsVM.getDateRangeType(), supplier, orderDateHbFrom, orderDateHbTo);


            orderStatDTO.setOrderAmtHb(dataHb.getOrderAmt());
            orderStatDTO.setPartCategoryQtyHb(BigDecimal.valueOf(data.getPartCategoryQty() ==0 ? 0 : dataHb.getPartCategoryQty()/data.getPartCategoryQty()));
            orderStatDTO.setContractAmtHb(dataHb.getContractAmt());
            orderStatDTO.setPaymentAmtHb(dataHb.getPaymentAmt());
        }
        return orderStatDTO;
    }

    private Instant [] quarterToInstant(String year, String quarter) {
        Instant orderDateFrom = null;
        Instant orderDateTo =null;
        switch (quarter) {
            case "1":
                orderDateFrom = DateUtils.parseDate(year+"-01-01", DateUtils.PATTERN_DATE).toInstant();
                orderDateTo = DateUtils.parseDate(year+"-03-31 23:59:59", DateUtils.PATTERN_DATEALLTIME).toInstant();
                break;
            case "2" :
                orderDateFrom = DateUtils.parseDate(year+"-04-01", DateUtils.PATTERN_DATE).toInstant();
                orderDateTo = DateUtils.parseDate(year+"-06-30 23:59:59", DateUtils.PATTERN_DATEALLTIME).toInstant();
                break;
            case "3":
                orderDateFrom = DateUtils.parseDate(year+"-07-01", DateUtils.PATTERN_DATE).toInstant();
                orderDateTo = DateUtils.parseDate(year+"-09-30 23:59:59", DateUtils.PATTERN_DATEALLTIME).toInstant();
                break;
            case "4":
                orderDateFrom = DateUtils.parseDate(year+"-10-01", DateUtils.PATTERN_DATE).toInstant();
                orderDateTo = DateUtils.parseDate(year+"-12-31 23:59:59", DateUtils.PATTERN_DATEALLTIME).toInstant();
        }
        Instant [] tmp = {orderDateFrom, orderDateTo};
        return tmp;
    }

    private OrderStatsDataDTO getStatData (String dataRangeType, Supplier supplier, Instant orderDateFrom, Instant orderDateTo  ) {
        OrderStatsDataDTO dataDTO = new OrderStatsDataDTO();

        //Part and Order
        Date dateFrom = Date.from(orderDateFrom);
        Date dateTo = Date.from(orderDateTo );

        List<Order> orders = orderRepository.findOrders4Stat(supplier.getId(),dateFrom , dateTo );

        BigDecimal totalAmtIncTax = orders.stream().map(order -> order.getOrderExtra().getAmtIncTax()).reduce(BigDecimal.ZERO,BigDecimal::add);

        //订单
        dataDTO.setOrderQty(orders.size());
        dataDTO.setOrderAmt(totalAmtIncTax);
        //零件
        Long partCnt = orders.stream().flatMap(order-> order.getOrderItems().stream()).map(orderItem -> orderItem.getMaterialCode()).distinct().count();
        dataDTO.setPartCategoryQty(partCnt);

        //contract

        ContractResDTO resDTO = contractInterfaceService.getContractStat(supplier.getSupplierCode().getCompcodeCd(), dateFrom, dateTo );
        if(resDTO.getCode() == 0){
            dataDTO.setContractAmt(BigDecimal.valueOf(resDTO.getTaxTotalContractAmt()));
            dataDTO.setPaymentAmt(BigDecimal.valueOf(resDTO.getTaxTotalPaymentAmt()));
        }else {
            dataDTO.setContractAmt(BigDecimal.ZERO);
            dataDTO.setPaymentAmt(BigDecimal.ZERO);
        }

        return dataDTO;
    }

    /**
     * 合同号批量写入SRM订单
     * @param orderUpdateQueryDTO
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateContractInfo(OrderUpdateQueryDTO orderUpdateQueryDTO) throws Exception {
        List<Order> orders =  orderRepository.findAllByOrderNumIn(orderUpdateQueryDTO.getOrderNums());
        if(orders == null || orders.size() == 0) {
            throw new Exception ("SRM订单都不存在， orders : " + orderUpdateQueryDTO.getOrderNums());
        }
        orders.forEach(order -> {
            order.setRefContractNum(orderUpdateQueryDTO.getContractNum());
            order.setRefContractName(orderUpdateQueryDTO.getContractName());
        });
        orderRepository.saveAll(orders);
    }

    public List<String> getAllMaterialCodes(String companyCode){
        return orderItemRepository.findAllItemCd(companyCode);
    }

    public List<String> getAllMaterialCodes4Supplier(String companyCode){
        return orderItemRepository.findAllItemCd4Supplier(companyCode);
    }


    /**
     * 1元订单价格更新
     * @param orderPriceUpdateQueryDTO
     * @return
     */
    public List<OrderPriceResultItemDTO> updateOrderPrice(OrderPriceUpdateQueryDTO orderPriceUpdateQueryDTO) {
        QueryParams<OrderItem> queryParams = new QueryParams<>();
        // 物料编码条件查询
        if (org.apache.commons.lang.StringUtils.isNotBlank(orderPriceUpdateQueryDTO.getMaterialCode())) {
            queryParams.and(new Filter("materialCode", Filter.Operator.eq, orderPriceUpdateQueryDTO.getMaterialCode().trim()));
        }
        // 供应商代码条件查询
        if (org.apache.commons.lang.StringUtils.isNotBlank(orderPriceUpdateQueryDTO.getSupplierCode())) {
            queryParams.and(new Filter("order.supplier.supplierCode.compcodeCd", Filter.Operator.like, orderPriceUpdateQueryDTO.getSupplierCode().trim()));
        }
        List<OrderItem> orderItemList = orderItemRepository.findAll(queryParams);

        List<OrderPriceResultItemDTO> orderPriceResultItemDTOList = new ArrayList<>();
        for (OrderItem orderItem : orderItemList) {
            //单价
            BigDecimal unitPrice = orderItem.getUnitPrice() != null ? orderItem.getUnitPrice().divide(orderItem.getUnit() !=null ? orderItem.getUnit() : BigDecimal.ONE) : BigDecimal.ZERO;
            if(unitPrice.compareTo(BigDecimal.ONE) == 0){
                orderItem.setUnitPrice(orderItem.getUnit().multiply(orderPriceUpdateQueryDTO.getUnitPrice()));
                if (StringUtils.isNotEmpty(orderPriceUpdateQueryDTO.getTaxCode())) {
                    List<TaxMapping> queryTaxTypes = taxMappingRepository.findAllByRefSysCd(orderPriceUpdateQueryDTO.getTaxCode());
                    if (null != queryTaxTypes && queryTaxTypes.size() > 0) {
                        //orderItem.setTaxType(queryTaxTypes.get(0).getTaxId());
                        orderItem.setTax(queryTaxTypes.get(0).getTaxAmount());
                        //update status
                        if(orderItem.getOrderItemExtendView() != null){
                            if(orderItem.getOrderItemExtendView().getDeliveryQty() == null || orderItem.getOrderItemExtendView().getDeliveryQty().compareTo(BigDecimal.ZERO) == 0 ){
                                orderItem.getOrderItemExtendView().setStatus(OrderItemStatusEnum.PENDING_DELIVERY);
                            }else if(orderItem.getOrderItemExtendView().getDeliveryQty().compareTo(BigDecimal.ZERO) == 1){
                                orderItem.getOrderItemExtendView().setStatus(OrderItemStatusEnum.PENDING_GR);
                            }
                            orderItem.getOrderItemExtendView().setInquiryStatus(InquiryStatusEnum.PRICE_UPDATED_DONE);
                        }
                    }
                }
                OrderPriceResultItemDTO orderPriceResultItemDTO = new OrderPriceResultItemDTO();
                orderPriceResultItemDTO.setOrderNum(orderItem.getOrderNum());
                orderPriceResultItemDTO.setOrderItemNum(orderItem.getOrderItemNum());
                orderPriceResultItemDTOList.add(orderPriceResultItemDTO);
            }
        }
        orderItemRepository.saveAll(orderItemList);
        return orderPriceResultItemDTOList;
    }

    /**
     * 创建1元询价单
     * @param ids
     */
    public void createInquiryOrderFromSourcingInterface(List<String> ids) {
        List<OrderItem> orderItems = orderItemRepository.findAllByIdIn(ids);
        if(orderItems!=null && orderItems.size() > 0){
            // 调用创建1元询价单接口
            StringBuffer errorInfo = new StringBuffer(); //错误信息

            OrderPriceResponseDTO response = sourcingInterfaceService.createInquiry(orderItems, errorInfo);
            if(errorInfo.length() > 0){
                log.error("In OrderService.createInquiryOrderFromSourcingInterface() error, please check the send interface,  errorInfo: {}", errorInfo);
                throw new BusinessException(errorInfo.toString());
            }else if(response.getIsSuccess() == 1){
                log.info("{}:{}", "成功创建一元订单询价单" ,JSONObject.toJSON(response));
                for (OrderPriceResponseItemDTO item : response.getItems()) {
                    orderItems.forEach(orderItem -> {
                        if (orderItem.getOrderNum().equals(item.getOrderNo()) && String.valueOf(orderItem.getOrderItemNum()).equals(item.getOrderItemNo())
                        &&orderItem.getOrderItemExtendView() !=null) {
                            orderItem.getOrderItemExtendView().setInquiryHdCd(item.getInquiryHdCd());
                            orderItem.getOrderItemExtendView().setInquiryStatus(InquiryStatusEnum.QUOTE_WAITING);
                        }
                    });
                }
                orderItemRepository.saveAll(orderItems);
                }
            }
        }
    
    @Transactional(readOnly = true)
    public List<OrderItemDTO> findAllPendingSourcingOrderItems() {

//        QueryParams<OrderItem> queryParams = new QueryParams<>();
//
//
//        queryParams.and(new Filter("order.purchaseType", Filter.Operator.ne, PurchaseTypeEnum.PARTIAL_OUTSOURCING));
//        queryParams.and(new Filter("isDeleted", Filter.Operator.ne, true));
//        queryParams.and(new Filter("orderItemExtendView.inquiryHdCd", Filter.Operator.isNull,null));
//        Specification<OrderItem> oneYuan = new Specification<OrderItem>() {
//
//			private static final long serialVersionUID = 104483544126723136L;
//
//			@Override
//			public Predicate toPredicate(Root<OrderItem> root, CriteriaQuery<?> query,
//					CriteriaBuilder criteriaBuilder) {
//				Predicate restrictions = criteriaBuilder.conjunction();
//				restrictions.getExpressions().add(criteriaBuilder.equal(criteriaBuilder.quot(root.get("unitPrice"), root.get("unit")),
//						criteriaBuilder.literal(1.0d)));
//				return criteriaBuilder.and(queryParams.apllySearchConditions(root, criteriaBuilder, restrictions));
//
//			}
//        };
//        List<OrderItem> ordersItems = orderItemRepository.findAll(oneYuan);
//
//        return ordersItems.stream().map(orderMapper::toItemDTO).collect(Collectors.toList());
        List<?> source = orderItemRepository.findAllPendingSourcing();
        List<OrderItemDTO> orderItems = new ArrayList<>();
        for (int i = 0; source != null && i < source.size(); i++) {
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            Object[] obj = (Object[]) source.get(i);
            orderItemDTO.setId(obj[0].toString());
            orderItemDTO.setSupplierCode(obj[1].toString());
            orderItemDTO.setDeptName(obj[2].toString());
            orderItems.add(orderItemDTO);
        }
        return orderItems;
    }

    public void updateInquiryStatusFromSourcingInterface(List<String> ids) {
        List<OrderItem> orderItems = orderItemRepository.findAllByIdIn(ids);
        if (orderItems != null && orderItems.size() > 0) {
            List<String> inquiryHdCds = orderItems.stream().map(orderItem -> orderItem.getOrderItemExtendView().getInquiryHdCd())
                    .distinct().collect(Collectors.toList());
            StringBuffer errorInfo = new StringBuffer();
            InquiryStatusResponseDTO response = sourcingInterfaceService.fetchInquiryStatus(inquiryHdCds, errorInfo);
            if (errorInfo.length() > 0) {
                log.error("In OrderService.updateInquiryStatusFromSourcingInterface() error, please check the send interface,  errorInfo: {}", errorInfo);
                throw new BusinessException(errorInfo.toString());
            } else if (response.getIsSuccess() == 1) {
                log.info("{}:{}", "成功查询一元订单询价单状态", JSONObject.toJSON(response));
                for (InquiryStatusResponseItemDTO item : response.getItems()) {
                    orderItems.forEach(orderItem -> {
                        if (orderItem.getOrderItemExtendView() != null && item.getInquiryCode().equals(orderItem.getOrderItemExtendView().getInquiryHdCd())) {
                            orderItem.getOrderItemExtendView().setInquiryStatus(InquiryStatusEnum.resolve(item.getInquiryStatus()));
                        }
                    });
                }
                orderItemRepository.saveAll(orderItems);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<OrderItem> findAllSourcingInquiry(List<InquiryStatusEnum> statusList, boolean isSourcing) {
        Specification<OrderItem> spec = Specification.where(null);
        spec = spec.and((Specification<OrderItem>) (root, query, builder) ->
                builder.notEqual(root.get("order").get("purchaseType"), PurchaseTypeEnum.PARTIAL_OUTSOURCING));
        spec = spec.and((Specification<OrderItem>) (root, query, builder) ->
                builder.notEqual(root.get("isDeleted"), true));
        if (isSourcing) {
            spec = spec.and((Specification<OrderItem>) (root, query, builder) ->
                    builder.isNotNull(root.get("orderItemExtendView").get("inquiryHdCd")));
        }
        Specification<OrderItem> status1 = (Specification<OrderItem>) (root, query, builder) -> {
            CriteriaBuilder.In<InquiryStatusEnum> in = builder.in(root.get("orderItemExtendView").get("inquiryStatus"));
            statusList.forEach(in::value);
            return in;
        };
        Specification<OrderItem> status2 = (Specification<OrderItem>) (root, query, builder) ->
                builder.isNull(root.get("orderItemExtendView").get("inquiryStatus"));
        spec = spec.and(status1.or(status2));

        Specification<OrderItem> oneYuan = new Specification<OrderItem>() {
            private static final long serialVersionUID = 104483544126723131L;

            @Override
            public Predicate toPredicate(Root<OrderItem> root, CriteriaQuery<?> query,
                                         CriteriaBuilder criteriaBuilder) {
                Predicate restrictions = criteriaBuilder.conjunction();
                restrictions.getExpressions().add(criteriaBuilder.equal(criteriaBuilder.quot(root.get("unitPrice"), root.get("unit")),
                        criteriaBuilder.literal(1.0d)));
                return restrictions;
            }
        };
        spec = spec.and(oneYuan);
        return orderItemRepository.findAll(spec);
    }
}

