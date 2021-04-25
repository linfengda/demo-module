### rabbitmq逻辑增强

一：目前代码存在的问题
1. 每个mq服务，都需要在配置类创建ConnectionFactory、RabbitTemplate、SimpleRabbitListenerContainerFactory等服务
2. 每次上线都要运维手动添加队列，容易出错
3. 没有消费重试

二：通过设计规范改善代码
1. 扫描@RabbitService注解，自动创建相关依赖服务
2. 扫描@RabbitQueue注解，解析并创建队列、交换机、绑定关系（暂与@RabbitListener解耦）
3. 开启reConsume属性，则自动消费重试