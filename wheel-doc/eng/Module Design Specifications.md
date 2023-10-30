#### Module Design Specifications

**This project is based on the Roses framework**

[https://gitee.com/stylefeng/roses.git](https://gitee.com/stylefeng/roses.git) (Apache License 2.0)

##### 1. Plugin Specifications

**Plugins are classified as follows:**

- Advanced, rules for the entire module, rules that all code must comply with, including enums, exceptions, basic classes, etc.
- Development, quick development tools for developers, facilitating rapid development, e.g., logging, email, SMS, caching, etc.
- Service, encapsulation of application-specific functions, such as user management, role management, company management, where each module represents an independent business.

The reference relationship between categories is generally that a service can reference development, and development can reference advanced.

**Plugins have some small Maven modules internally:**

- api, the interface layer of the plugin, abstracts the interface of this module, responsible for providing interfaces for external modules.
- sdk, the implementation layer of the plugin, without business logic, pure internal implementation of the plugin.
- business, the implementation layer of the plugin, with business logic (generally with CRUD operations).
- starter, the plugin's spring boot starter encapsulation, automatic configuration, and starter dependencies for quick integration into projects.
- integration, further encapsulation on top of the starter for quick integration with different architectures, e.g., the RESTful architecture and architecture without separating Beetl.

The internal reference relationship is generally from bottom to top, meaning integration can reference starter and so on.

##### 2. Module Type A

There is only one A-type module, which is kernel-a-rule, and this module is referenced by all other modules. It is the most basic part of the entire project.

In the pom.xml of this module, packages related to Hutool, Lombok, Fastjson, and Spring Boot are referenced, setting the tone for the entire framework and project.

The core components of this module include:

- 1. Definition of some basic constants for the project.
- 1. Establishment of the project's exception specifications. Refer to AbstractExceptionEnum and ServiceException for details.
- 1. Request and response entities, including BaseRequest, ResponseData, SuccessResponseData, and ErrorResponseData.
- 1. Supplemental utility classes not encapsulated in Hutool but may be used in the project, such as MacAddressUtil and ResponseRenderUtil.
- 1. Provision of commonly used system enums, such as StatusEnum and YesOrNotEnum.

##### 3. Module Type D

The most extensive category of plugins, including the project's core permission plugin, cache plugin, system configuration, database operations, file handling, SMS handling, logging, resource scanning, and more.

Most of these plugins have already been referenced in the Guns project. If not referenced, you can refer to the calling method in section 4.3 to reference them. The following sections will provide a detailed introduction to the functionality and usage of each plugin.

##### 4. Module Type S

This category includes the primary business components that make up the project, referring to the backend logic for CRUD operations corresponding to frontend business interfaces.

These business modules encompass all backend interface implementations for frontend interfaces. Generally, each business module includes related controller, service, entity, and mapper packages.