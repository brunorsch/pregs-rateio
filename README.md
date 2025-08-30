# Pregs Rateio
App para dividir gastos recorrentes com amigos, permitindo automatização de lembretes e controle de pagamentos, atrasos e afins. 

O projeto não tem o objetivo de servir como um produto real, não temos uma expectativa de rentabilizar o mesmo. Serve, portanto, apenas como portfolio e 
como laboratório para teste de novas tecnologias, padrões de projeto diferentes do que estamos acostumados a usar no trabalho, e coisas do tipo. É uma
forma de validar outras possibilidades de ferramentas, padrões, arquitetura, etc. Além de servir também como template de projeto para eventuais outros projetos nessas tecnologias.

## Executando local
Pode-se usar o docker-compose.yml raiz do projeto para executar o servidor e o banco de dados locamente

```sh
docker-compose up -d
```

## Detalhamento técnico
### Decisões e motivações de tecnologia - Back-end:
> 💡 O texto escrito aqui é de autoria do Bruno Schmidt ([@brunorsch](https://github.com/brunorsch)), e está em formato de texto pessoal de propósito.
- **Java/Spring**: A ideia era possibilitar ao [@RafaelSantini23](https://github.com/RafaelSantini23) a se familiarizar com a stack do Java, por isso optamos por (re-)construir o projeto em Java/Spring Boot.
- **MongoDB**: Porque Mongo lida bem com o nível de carga que esse projeto vai ter, e também porque eu não queria perder tempo escrevendo migrations.
- **Auth0**: Para testar na prática o nível de complexidade de se configurar e usar o Auth0 num projeto. *(⚠️ Achei a implementação bem confusa, 
e fiquei bastante perdido em vários momentos, pode ser puramente por falta de prática e de conhecimento de segurança da minha parte, mas é interessante ter um ChatGPT por perto 
pra ajudar a lidar com essa integração)*
- **MapStruct**: Mesma ideia da familiarização ao ecossistema do Java, além de explorar práticas avançadas da lib.
