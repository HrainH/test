FROM openjdk:17-jdk-slim

LABEL maintainer=rain

# 设置工作目录
WORKDIR /app

# 拉取 GitHub 仓库的代码
RUN git clone -b master https://github.com/HrainH/test.git /app

# 构建项目 (假设使用 Maven)
RUN mvn clean package

# 复制编译好的 JAR 文件到容器中
COPY target/KafkaDemo-0.0.1-SNAPSHOT.jar /app

# 暴露容器的端口，Spring Boot 默认使用 8080 端口
EXPOSE 8070

# 容器启动时运行的命令
CMD ["java", "-jar", "/app/KafkaDemo-0.0.1-SNAPSHOT.jar"]