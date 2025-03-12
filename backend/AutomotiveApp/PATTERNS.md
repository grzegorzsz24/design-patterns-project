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
Interpreter
Iterator
Mediator
Memento
Observer:
    - Logger/LogHandler
State
Strategy:
    - LogFilter/LogLogFilter,WarnLogFilter,ErrorLogFilter
    - LogFormatter/TextLogFormatter,JsonLogFormatter,XmlLogFormatter
    - LogHandler/ConsoleLogHandler,FileLogHandler,CompositeLogHandler
Template:
    - BaseLogHandler
    - NotificationFactory
Visitor