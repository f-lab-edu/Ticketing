# πΏ Ticketing
μν μλ§€ μ¬μ΄νΈλ₯Ό λμ©λ νΈλν½μ λμν  μ μλλ‘ μ€κ³.

<br/>

## π νλ‘μ νΈ κΈ°κ°
### 22.04.13 ~ 
- κΈ°ν λ° μ€κ³ : 22.04.13 ~ 
- νλ‘μ νΈ κ΅¬ν :   

<br/>

## π― νλ‘μ νΈ μ£Όμ κ΄μ¬μ¬
- OOP(κ°μ²΄ μ§ν₯ νλ‘κ·Έλλ°)μ μ₯μ μ μ΅λν νμ©
- νμ€νΈ μ½λλ₯Ό ν΅ν λ―Ώμ μ μλ μ½λ μμ±
- λμ©λ νΈλν½μ κ³ λ €ν νμ₯ κ°λ₯ν μ€κ³ λ° μ±λ₯ νλ
- μ½λλ¦¬λ·°λ₯Ό ν΅ν΄ μ½λ νμ§ ν₯μ
- μ½λ μ»¨λ²€μ μ€μνμ¬ μ½λ ν΅μΌμ± μ μ§

<br/>

## π  κΈ°μ μ€ν
- Java 11
- Spring Boot 2.6.7 (λΉμ μ΅μ  GA λ²μ )
- Gradle Kotlin DSL
- Spring Security
- Junit 5
- Hibernate / SpringJPA
- MySQL 8.0
- Redis

<br/>

## Wiki
- [Git Branch μ λ΅](https://github.com/f-lab-edu/Ticketing/wiki#-git-branch-%EC%A0%84%EB%9E%B5)
- [Code Convention](https://github.com/f-lab-edu/Ticketing/wiki#-code-convention)
- [ν¨ν€μ§ κ΅¬μ‘°](https://github.com/f-lab-edu/Ticketing/wiki#-%ED%8C%A8%ED%82%A4%EC%A7%80-%EA%B5%AC%EC%A1%B0)
- [Use Case](https://github.com/f-lab-edu/Ticketing/wiki/Use-Case)
- [Prototype](https://github.com/f-lab-edu/Ticketing/wiki/Prototype)
- [Issue Posting](https://github.com/f-lab-edu/Ticketing/wiki/Issue-Posting)

<br/>

## π§© ERD

``` mermaid
erDiagram
    MOVIE ||--o{ MOVIE_TIME : ""
    MOVIE {
        bigint id PK "μν ID"
        varchar title "μνμ λͺ©"
        int running_time "λ¬λνμ"
        datetime deleted_at "μ­μ μΌμ"
        datetime created_at "λ±λ‘μΌμ"
        datetime updated_at "μμ μΌμ"
    }
    THEATER ||--o{ MOVIE_TIME : ""
    THEATER ||--|{ SEAT : ""
    THEATER {
        bigint id PK "μμκ΄ ID"
        int theater_number "μμκ΄ λ²νΈ"
        int seat_count "μ’μμ"
        datetime deleted_at "μ­μ μΌμ"
        datetime created_at "λ±λ‘μΌμ"
        datetime updated_at "μμ μΌμ"
    }
    SEAT ||--o{ TICKET : ""
    SEAT {
        bigint id PK "μ’μ ID"
        bigint theater_id FK "μμκ΄ ID"
        int column "μ΄"
        int row "ν"
        datetime deleted_at "μ­μ μΌμ"
        datetime created_at "λ±λ‘μΌμ"
        datetime updated_at "μμ μΌμ"
    }
    MOVIE_TIME ||--o{ TICKET : ""
    MOVIE_TIME {
        bigint id PK "μμμκ°ν ID"
        bigint movie_id FK "μν ID"
        bigint theater_id FK "μμκ΄ ID"
        int round "νμ°¨"
        time start_at "μμ μκ°"
        time end_at "μ’λ£ μκ°"
        datetime deleted_at "μ­μ μΌμ"
        datetime created_at "λ±λ‘μΌμ"
        datetime updated_at "μμ μΌμ"
    }
    TICKET {
        bigint id PK "ν°μΌ ID"
        bigint seat_id FK "μ’μ ID"
        bigint movie_time_id FK "μμμκ°ν ID"
        bigint payment_id "κ²°μ  ID"
        varchar status "μν - νλ§€κ°λ₯/μμ½/"
        int ticket_price "κ°κ²©"
        datetime deleted_at "μ­μ μΌμ"
        datetime created_at "λ±λ‘μΌμ"
        datetime updated_at "μμ μΌμ"
    }
    TICKET }|--|| PAYMENT : ""
    PAYMENT {
        bigint id PK "κ²°μ  ID"
        bigint user_alternate_id "μ μ  λμ²΄ID"
        varchar tid "μΉ΄μΉ΄μ€νμ΄ κ²°μ κ³ μ λ²νΈ"
        varchar movie_title "μνμ λͺ©"
        varchar type "κ²°μ  νμ - μ) λ€μ΄λ²νμ΄, μΉ΄μΉ΄μ€νμ΄"
        varchar status "μν - μλ£/νλΆ"
        varchar failed_message "μ€ν¨μ¬μ "
        varchar payment_number "μλ§€λ²νΈ"
        int total_price "κ²°μ  κΈμ‘"
        datetime deleted_at "μ­μ μΌμ"
        datetime created_at "κ²°μ μΌμ"
        datetime updated_at "μμ μΌμ"
    }
    USER ||--o{ PAYMENT : ""
    USER {
        bigint id "νμ"
        bigint alternate_id "λμ²΄ID"
        varchar name "μ΄λ¦"
        varchar email "μ΄λ©μΌ"
        varchar password "λΉλ°λ²νΈ"
        varchar grade "λ±κΈ - κ³ κ°/μμ§μ"
        varchar phone "ν΄λν° λ²νΈ"
        datetime deleted_at "νν΄μΌμ"
        datetime created_at "κ°μμΌμ"
        datetime updated_at "μμ μΌμ"
    }
```
