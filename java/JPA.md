1. Relationship (Owning side has foreign key, other side use mappedBy)

- OneToOne (Eager)
- OneToMany (Lazy)
- ManyToOne (Eager)
- ManyToMany (Lazy)

```
    class Answer {
        @ManyToMany(cascade = CascadeType.ALL)
        @JoinTable(name = "ANSWERS_COLLABORATORS",
        joinColumns = { @JoinColumn(name = "aid") },
        inverseJoinColumns = { @JoinColumn(name = "cid") })
        private Set<Collaborator> collaborators = new HashSet<Collaborator>(0);
    }


    public List<Collaborator> getCollaborators(Long answerId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Collaborator> criteriaQuery = criteriaBuilder
                .createQuery(Collaborator.class);
        Root<Answer> answerRoot = criteriaQuery.from(Answer.class);
        criteriaQuery.where(criteriaBuilder.equal(answerRoot.get(Answer_.id),
                answerId));
        SetJoin<Answer, Collaborator> answers = answerRoot
                .join(Answer_.collaborators);
        CriteriaQuery<Collaborator> cq = criteriaQuery.select(answers);
        TypedQuery<Collaborator> query = entityManager.createQuery(cq);
        return query.getResultList();

    }
```

I have a Many-To-Many relationship between Schedule and Agent (via the property assignedAgents).

I want to find Schedules that contain ANY of the agents I supply. I am trying to do this:

List<Agent> agentsToMatch = // ... I want schedules with any of these agents

CriteriaBuilder cb = session.getCriteriaBuilder();
CriteriaQuery<Schedule> query = cb.createQuery(Schedule.class);
Root<Schedule> schedule = query.from(Schedule.class);
query.where(schedule.get(Schedule\_.assignedAgents).in( agentsToMatch ));

change to

query.where(schedule.join(Schedule\_.assignedAgents).in( agentsToMatch ));

//投产批次
if(StringUtils.isNotBlank(orderVM.getOperationBatch())){
Specification<Order> itemSpecification= new Specification<Order>(){
@Override
public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
Join<Order, OrderItem> join = root.join("orderItems");
Predicate operationBatch = criteriaBuilder.like(join.get("operationBatch"), "%"+orderVM.getOperationBatch().trim()+"%");
return operationBatch;
}
};
specification = specification.and(itemSpecification);

        }

Specification specification = new Specification<OrderItem>() {
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
