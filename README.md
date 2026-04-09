# Java Demo

Java 技术学习与演示项目，采用 Maven 多模块架构，涵盖设计模式、算法、并发编程、JVM 底层机制、动态代理等核心技术。

## 项目结构

```
java-demo
├── pom.xml                          # 根 POM（聚合项目）
├── java-base                        # 核心演示模块
│   └── src/main/java/com/fly/demo/base/
│       ├── Main.java                # 入口类
│       ├── constant/                # 常量定义
│       ├── entity/                  # 实体类
│       │   └── design_pattern/      # 设计模式演示
│       ├── exception/               # 异常处理
│       ├── test/                    # 技术演示
│       └── util/                    # 工具类
└── java-common                      # 公共依赖父模块
    ├── pom.xml                      # 依赖版本管理（BOM）
    └── java-common-core/            # 公共核心模块
        └── src/main/java/com/fly/demo/
            ├── entity/              # 通用实体
            ├── deserializer/        # 反序列化工具
            └── util/                # 工具类
```

## 技术栈

| 类别 | 技术 |
|------|------|
| **Java 版本** | Java 17 |
| **构建工具** | Maven |
| **Spring 生态** | Spring Boot 3.4.3、Spring Cloud 2024.0.0、Spring Cloud Alibaba |
| **数据库** | MySQL、PostgreSQL、达梦、人大金仓 |
| **ORM 框架** | MyBatis 3.0.5 |
| **缓存** | Redisson |
| **消息队列** | RocketMQ |
| **RPC** | gRPC |
| **对象存储** | MinIO、AWS S3 |
| **网络通信** | Netty、OkHttp、HttpClient |
| **安全认证** | Spring Security OAuth2、JWT |
| **日志** | Log4j2 |
| **工具库** | Hutool、Apache Commons、Lombok |

## 模块说明

### java-base

核心演示模块，包含以下功能：

#### 设计模式
- **创建型模式**：简单工厂、抽象工厂、静态工厂、单例模式（饿汉式/懒汉式）
- **结构型模式**：装饰器模式

#### 算法实现
- **排序算法**：冒泡排序、选择排序、插入排序、归并排序、快速排序
- **查找算法**：二分查找（递归/非递归）
- **字符串匹配**：BF、KMP、BM、Sunday 算法

#### 并发编程
- ReentrantLock 测试
- Synchronized 测试
- ThreadLocal 测试
- 线程池测试
- 生产者-消费者模式

#### JVM 底层
- 软引用演示
- GC 测试
- Finalizer 逃逸分析
- Unsafe 类使用

#### 动态代理
- JDK 动态代理
- CGLIB 动态代理

#### 安全与加密
- JWT Token 生成与验证
- RSA 加密
- MD5/HMAC 加密

### java-common

依赖版本管理父模块（BOM），统一管理所有依赖版本。

### java-common-core

公共核心模块，提供通用工具类：
- **文件操作**：FileUtil
- **JSON 处理**：GsonUtil、JsonUtil
- **时间处理**：TimeUtil
- **分页工具**：PageUtil
- **响应封装**：R（统一响应实体）

## 快速开始

### 环境要求
- JDK 17+
- Maven 3.6+

### 编译项目

```bash
mvn clean install
```

### 运行示例

```bash
cd java-base
mvn exec:java -Dexec.mainClass="com.fly.demo.base.Main"
```

## 依赖管理

项目使用 Maven BOM 机制统一管理依赖版本，引入方式：

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.fly.demo</groupId>
            <artifactId>java-common</artifactId>
            <version>${project.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

## License

MIT License