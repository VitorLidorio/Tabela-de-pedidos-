> Passo a passo para fazer a implantação:
--------------------------------------------------------------------------------------------------------------------------------------
> Copiar a pasta do diretório para onde quiser fazer a instalação
> É necessário ter o Apache Maven na versão 3.9.6 instalada, para verificar, basta colar o código no prompt (mvn -version)
> É necessário ter o SpringBook na versão 3.2.1 e o java na versão 17 (java -version)
--------------------------------------------------------------------------------------------------------------------------------------
> Na pasta onde está localizado o arquivo "pom.mxl" é necessário executar via prompt (mvn clean package -DskipTests) para iniciar a build
> Em seguida executar  (java -jar target/pedidos-0.0.1-SNAPSHOT.jar) para iniciar o serviço do SpringBoot

> http://localhost:8086