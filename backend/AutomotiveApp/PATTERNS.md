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
Command:
    - SendInvitationCommand
    - AcceptInvitationCommand
    - RemoveFriendCommand
Interpreter:
    - Interpreter
Iterator:
    - CommentIterator
    - ForumIterator
    - FriendshipIterator
Mediator:
    - Logger
    - EventBus
    - ReportMediator
    - LikeMediator
Memento
Observer:
    - Logger/LogHandler,ConsoleLogHandler,FileLogHandler,CompositeLogHandler
    - EventBus/EventHandler
State:
    - InvitationState
Strategy:
    - LogFilter/LogLogFilter,WarnLogFilter,ErrorLogFilter
    - LogFormatter/TextLogFormatter,JsonLogFormatter,XmlLogFormatter
    - LogHandler/ConsoleLogHandler,FileLogHandler,CompositeLogHandler
Template:
    - BaseLogHandler
    - NotificationFactory
    - FileStorageService
Visitor:
    - Visitor
    - ForumDtoCollectorVisitor
    - PostDtoCollectorVisitor
    - InvitationStateVisitor

## Solid

Single responsibility
Open–closed
Liskov substitution:
    - ArticleFactory
    - LogHandler
    - LogFilter
Interface segregation:
    - ArticlePersistenceService
    - CommentPersistenceService
    - PostPersistenceService
Dependency inversion
    - LogFilter
    - LogFormatter
    - LogHandler
    - ReportService
    - CarService
    - ChannelService

## Other
Refactoring:
    - clear naming scheme (checkstyle)
    - method length (checkstyle)
    - one responsibility functions (manual)
    - abstraction level
    - max 3 parameters (checkstyle)
    - exceptions instead of error codes (manual)
    - no duplicate code (ide)
    - no magic numbers (checkstyle)
Functional interfaces:
    - LogFilter
    - LogFormatter
    - LogHandler
Streams:
    - Interpreter
    - PostService
    - ChannelService
AspectJ:
    - AuditAspect
    - RateLimitingAspect
    - RetryAspect
Bad practices:
    - TheMostEfficientAndTotallyNotBloatedLoggingSystemEverDesigned
