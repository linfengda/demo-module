### rabbitmq消费逻辑增强

一：目前代码存在的问题
1. 每个mq消费监听都需要配置一个SimpleRabbitListenerContainerFactory实例
2. 每次上线都要运维手动添加队列，容易出错
3. 没有消费重试

二：通过设计规范改善代码
1. 使用@ConsumerService注解表示mq消费服务
2. 使用@ConsumerQueue注解表示mq队列消费
3. 自动化消费重试