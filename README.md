# Pregs Rateio
App para dividir gastos recorrentes com amigos, permitindo automatização de lembretes e controle de pagamentos, atrasos e afins. 

O projeto não tem o objetivo de servir como um produto real, não temos uma expectativa de rentabilizar o mesmo. Serve, portanto, apenas como portfolio e 
como laboratório para teste de novas tecnologias, padrões de projeto diferentes do que estamos acostumados a usar no trabalho, e coisas do tipo. É uma
forma de validar outras possibilidades de ferramentas, padrões, arquitetura, etc. Além de servir também como template de projeto para eventuais outros projetos nessas tecnologias.

## Detalhamento técnico
### Decisões e motivações de tecnologia - Back-end:
> 💡 O texto escrito aqui é de autoria do Bruno Schmidt (@brunorsch), e está em formato de texto pessoal de propósito.
- **Kotlin**: Apenas porque já estou bem acostumado com Java no trabalho, e não sinto que tenha nada de novo de explorar que eu não consiga explorar no trabalho; Kotlin é um ótimo *syntatic sugar*
 do Java e é bastante divertido e prazeroso desenvolver back-end com ele. Além da sintaxe mais concisa, os recuros de null-safety são ótimos para se trabalhar no back-end.
- **Micronaut**: Testar uma alternativa ao Spring que parece bastante promissora. Também para servir de template para projetos futuros. A forma
como o Micronaut utiliza a geração de código para reduzir o uso de recursos e permitir um startup time menor são vantagens bem interessantes que merecem
ser validados num projeto real. *(⚠️ Sofri bastante com as documentações do `micronaut-security`, são bem menos abragentes que as docs do Spring)*
- **MongoDB**: Porque Mongo lida bem com o nível de carga que esse projeto vai ter, e também porque eu não queria perder tempo escrevendo migrations.
- **Auth0**: Para testar na prática o nível de complexidade de se configurar e usar o Auth0 num projeto. *(⚠️ Achei a implementação bem confusa, 
e fiquei bastante perdido em vários momentos, pode ser puramente por falta de prática e de conhecimento de segurança da minha parte, mas é interessante ter um ChatGPT por perto 
pra ajudar a lidar com essa integração)*
- **Redoc e Swagger UI**: O redoc foi utilizado para leitura de documentação, que na minha visão tem uma UI muito melhor para isso, mas mantive o Swagger UI pra facilitar testes, uma vez que
essa versão do Redoc que vem junto com o `micronaut-openapi` não tinha feature de "client" para realizar as requests diretamente. 
