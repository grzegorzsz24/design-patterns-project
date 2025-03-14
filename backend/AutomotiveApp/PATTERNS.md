# Design Patterns

## Creational Patterns
Factory:
    - ArticleFactory
    - MessageFactory
    - NotificationFactory
Singleton:
    - MessageService
    - NotificationService
    - ValidationService
Builder:
    - FriendshipBuilder
    - InvitationBuilder
    - UserBuilder
Prototype:
    - ApprovedArticleFactory
    - PendingArticleFactory
    - FileHandler
    - RoleName

## Structural Patterns
Adapter:
    - LikeRequestAdapter
    - MessageRequestAdapter
    - NotificationRequestAdapter
Composite:
    - MediaComponent
    - ContentComponent
    - BaseLogHandler
Bridge:
    - CarService/CarRepository
    - ReportService/ReportRepository
    - SearchService/*Repository
Decorator:
    - AuthorizationPostServiceDecorator
    - FallbackArticleServiceDecorator
    - LogFilterDecorator
    - LogFormatterDecorator
Facade:
    - SearchServiceImpl
    - FriendshipService
    - LikeService
Proxy:
    - CarCacheProxy
    - MessageCacheProxy
    - NotificationCacheProxy
Flyweight:
    - ReportType
    - RoleName
    - UserFriendshipStatus

## Behavioral
Command
Interpreter:
    - Interpreter
Iterator
Mediator
Memento
Observer:
    - Logger/LogHandler,ConsoleLogHandler,FileLogHandler,CompositeLogHandler
State
Strategy:
    - LogFilter/LogLogFilter,WarnLogFilter,ErrorLogFilter
    - LogFormatter/TextLogFormatter,JsonLogFormatter,XmlLogFormatter
    - LogHandler/ConsoleLogHandler,FileLogHandler,CompositeLogHandler
Template:
    - BaseLogHandler
    - NotificationFactory
Visitor:
    - Visitor

## Solid

Single responsibility
Open–closed
Liskov substitution
Interface segregation
Dependency inversion

## Other
Linter
Functional interfaces:
    - LogFilter
    - LogFormatter
    - LogHandler
Streams:
    - Interpreter
    - PostService
    - ChannelService
AspectJ
Bad practices:
    - TheMostEfficientAndTotallyNotBloatedLoggingSystemEverDesigned
