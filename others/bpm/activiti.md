#  ProcessEngineConfiguration

# ProcessEngine

## RepositoryService
### DeploymentBuilder & DeploymnetQuery
- deployment:    
  1. can deploy multiple definition files one time
  2. DeploymentQuery

### ProcessDefinitionQuery
- process definition
  1. id: key:version+globalIncreaseId
  2. suspend/active Process definition
  3. associate/delete candidate user/group to Process Definitioin (IdentityLink)
  4. query candidate user/group by Process Definition Id
- Java BpmModel 

## RuntimeService

- start process instance
 ***businessKey*** can be orderId,  txnId etc.
 ***can start by FormService.submitStartFormData() also..***
 1. startProcessInstanceByKey( key, params, bizKey)
 2. startProcessInstanceById( id, params)
 3. ProcessInstanceBuilder.businessKey.start()

- ProcessInstance variable:
 1. getVariables
 2. setVariables

- get Process Instance
 1. createPrcessInstanceQuery

- ExcutionQuery Excution(= execute path...) (Many to one ) -> ProcessInstance
  1. trigger receiveTask
  2. Signal EventReceived (Signal is global )
   ```
    <signal id ="signalStart" name="my-signal"/>
    <intermediateCatchEvent>
        <signalEventDefinition signalRef="signalStart">
    </intermediateCatchEvent>
   ```
  3. messageEventReceived (only for one ProcessInstance)
   ```
    <message id ="messageStart" name="my-message"/>
    <intermediateCatchEvent>
        <signalEventDefinition messageRef="signalStart">
    </intermediateCatchEvent>
   ```

## TaskService
 - UserTask Manage & Control

     1. create/delete Task (use rarely)
     2. search Task: TaskQuery
     3. complete task
       ```
        taskService.complete(taskId, variables)
       ```
     4. set/get task variable/local variable
     5. setOwner(taskId, "userId"), setAssinee(taskId, "userId")
     6. claim()
     7. createAttachment(type, taskId, desc,url)/getAttachments(taskIDd)
     8. addComment()/getTaskComments
     9. getTaskEvent(): comment, assign, owner

## IdentiyService
 - create user
 - creat groug

## FormService
- start form
 1. getStartFormKey
 2. getStartFormData()
 3. formDate.getFormProperties
 4. submitStartFormData() --> start ProcessInstant
- user task form
 1. getTaskFormKey
 2. getTaskSFormData()
 3. formData.getFormProperties()
 4. submitTaskFormData() == TaskService.complete()

## HistoryService
- HistoricProcessInstance
- HistoricVariableInstance
- HistoricActivityInstance
- HistoricTaskInstance
- HistoricDetail

1. createXXXQuery
2. creteNativeXXXQuery
3. createProcessInstanceHistoryLogQuery
4. deleteProcessInstance
5. deleteTaskInstance

## ManagementService
- Job Query:
 1. JobQuery
 2. TimerJobQuery
 3. SuspendedJobQuery
 4. DeadLetterJobQuery

- database:
 1. TableMetaData
 2. TablePageQuery
   ```
   manageService.createTablePageQuery().
   tableName(mgs.getTableName(ProcessDefinitionEntity.class))
   ```
 3. executeCustomQuery
 4. executeCommand

 
## DynamicBpmService

# Event 
 - Component
  1. EventListener (ActivitiListener)
  2. ActivitiEvent
  3. ActivitiEventType
 - register in in config: eventListers
 - EventLog(enableDatabaseEventLogging=true):AtivitiEventListener
  1. Process Start/End
  2. Activity Start/End
  3. variable
  4. SequernceFlow Taken
  5. Task

 # Interceptor 

 # Job Executor
```
 asyncExecutorActivate = true
 //DefaultAsycJobExecutor
 ayncExecutor ="xxx"
//bpmn:
    <startEvent id="startevent" name="开始" >
    	<timerEventDefinition>
    		<timeCycle>R5/PT10S</timeCycle>
    	</timerEventDefinition>
    </startEvent>

 ```

 # Srping Integration
 - SpringProcessEngineConfiguration
 - PrcessEngineFactoryBean
 
 # Database:
  1. ACT_GE_* GeneralTable

  2. ACT_RE_* Repository
    - ACT_RE_PROCDEF: Process DefinitionEntityImpl

  3. ACT_ID_* Identity

  4. ACT_RU_* Runtime
   - ACT_RU_EXECUTION:  ProcessInstance and Execution(IS_SCOPE: 0)
   - ACT_RU_TASK:  User task info
   - ACT_RU_VARIABLE: Variable Info
   - ACT_RU_IDENTITYLINK: AssignUser
   - ACT_RU_EVENT_SUBSCR: Event Subscriber
    ```
        <startEvent>
            <meessageEventDefinition messageRef="msgId">
        <startEvent/>
    ```
   - ACT_RU_JOB: Job
   - ACT_RU_TIMER_JOB: timer
   - ACT_RU_SUSPENDED_JOB: suspended job
   - ACT_RU_DEADLETTER_JOB: dead letter job

  5. ACT_HI_* History

# BPMN

## Flow Objects

1. Activity
 - User Task
   ```
   <userTask id="submitForm" name="填写审批信息"  activiti:dueDate activiti:candidateUsers ="user1, user2" activiti:CandidateGroups="grp1, grp2">
      <extensionElements>
        <activiti:formProperty id="message" name="申请信息" type="string" required="true"></activiti:formProperty>
        <activiti:taskListener event="create" class="XXXListener">
      </extensionElements>
    </userTask>
  

   ```
 - Service Task
    ```
     //in bpmn file
      <serviceTask activiti:class ="MyJavaDelegate">  
        <extensionElements>
           <activiti:field name ="prop" stringValue ="Hello"/>
        </extensionElements>
      <serviceTask/>

      <serviceTask activiti:expression ="${bean.method()}"/>  


      //java 
      //Process will continue.
      class MyJavaDelegate implement JavaDelegate {
          private Expression prop;

          public void execute(DelegateExecution excution){
              //...
          }
      }

      //another interface: ActivitiBehavior
      //Process will wait, need to call ActivitiEngineAgenda manually to contiue 
      class MyJavaDelegate implement ActivitiBehavior {
          public void execute(DelegateExecution excution){
              //...
          }
      }


    ```
 - Send Task

 - Receive Task

 - Script Task
    script type:
        - JUEL
        - Groovy
        - JavaScript
    predfined variable:
       - out
       - out:print
       - context
       - elcontext
    ```
        <scriptTaskTask scriptFormat ="groovy" activiti:resultVariable ="mySum">
            <script>
                def myKey ="myKey"
                execution.setVariable("key", myKey)
            </script>
        </scriptTask>

    ```

2. Events
 - Event
 - Intermediate Event
 - End Event
 - Error Event
    ```
    <error id ="myError" errorCode ="err_code">

    <endEvent>
    <errorEventDefinition errorRef="myError"/>
    </endEvent>
    ```
  - Signal Event
  - Message Event

3. Gateway & Sequence Flow
 - Exclusive: X
 - Parallel : +
 - Inclusive: O
 - event-based: 

 4. Sub-Processes & Call Activities
 
