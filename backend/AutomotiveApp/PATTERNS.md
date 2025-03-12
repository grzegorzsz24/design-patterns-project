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
Bridge:
    - CarService/CarRepository
    - ReportService/ReportRepository
    - SearchService/*Repository
Decorator:
    - AuthorizationPostServiceDecorator
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
Observer
State
Strategy
Template
Visitor