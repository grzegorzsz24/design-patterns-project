INSERT INTO article
(title,
 content,
 published_at,
 is_liked,
 likes_number,
 user_id,
 approved,
 article_type
)
VALUES
    (
        'Nowoczesne technologie w motoryzacji: Przyszłość na kołach',
        'Czy motoryzacja może być jeszcze bardziej innowacyjna? ...',
        '2023-11-06 12:00:12',
        FALSE,
        0,
        1,
        FALSE,
        'PENDING'
    ),
    (
        'Historia legend motoryzacji: Klasyczne samochody, które zawsze są w modzie',
        'Przeżyj podróż przez czas, odkrywając klasyczne samochody...',
        '2023-11-06 12:00:12',
        FALSE,
        0,
        2,
        FALSE,
        'PENDING'
    ),
    (
        'Elektryczne rewolucje: Przyszłość pojazdów elektrycznych',
        'Wejdź z nami w erę pojazdów elektrycznych...',
        '2023-11-06 12:00:12',
        FALSE,
        0,
        3,
        TRUE,
        'APPROVED'
    );
