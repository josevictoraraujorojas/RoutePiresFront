# Route Pires – Aplicativo de Mobilidade Mototaxista
## Documentação Oficial – README.md
## Descrição do Sistema

O Route Pires é um aplicativo Android desenvolvido para gerenciamento completo de corridas com mototaxistas. Ele permite que passageiros solicitem corridas, que mototaxistas recebam e executem serviços, e que ambos realizem comunicação direta, avaliações, denúncias e acompanhamento de status em tempo real.

O sistema foi projetado seguindo a arquitetura MVVM, garantindo organização, escalabilidade e clara separação de responsabilidades.
A interface foi criada com base em protótipos interativos que contemplam:

Fluxo de cadastro de passageiros e mototaxistas

Sistema de busca de corridas

Acompanhamento em mapa (GPS)

Chat, negociação e avaliação

Controle interno de chamadas, entregas e histórico

Sistema de notificações

## Arquitetura e Tecnologias Utilizadas
### Arquitetura

MVVM – Model View ViewModel

Navigation Component

Data Binding / View Binding

LiveData / StateFlow

Repository Pattern

Camada Remote + Retrofit

## Tecnologias
Tecnologia	Uso
Kotlin	Linguagem principal
Android Jetpack	Lifecycle, ViewModel, LiveData
Retrofit + Gson	Comunicação com API REST
Google Maps SDK	Exibição e manipulação do mapa
Material Design 3	Componentes visuais
Firebase / FCM (caso implementado)	Push notifications / Auth
Coroutines / Flow	Operações assíncronas

## Estrutura de Diretórios (MVVM)

Baseado na organização final definida no documento:


app/

└── src/

└── main/

├── java/com/routepires/

│   ├── data/

│   │   ├── model/

│   │   ├── repository/

│   │   └── remote/

│   ├── viewmodel/

│   └── ui/

│       ├── comum/

│       ├── mototaxista/

│       └── passageiro/

│
└── res/

├── layout/

├── values/

└── drawable/


### DATA

Models: Representação dos dados

Remote: Retrofit, serviços, DTOs

Repository: Regras de acesso aos dados

### VIEWMODEL

Controla fluxo da tela

Expõe estados (LiveData/Flow)

Conecta UI ↔ Repository

### UI

Separado pelos dois tipos de usuários e telas comuns:

ui/comum/
ui/mototaxista/
ui/passageiro/

## Instruções de Execução
### 1. Requisitos

Android Studio Flamingo ou superior

JDK 17

Emulador ou dispositivo Android 8.0+

Google Play Services / Maps API ativa

### 2. Clonar o projeto
git clone https://github.com/seu-repo/route-pires.git

### 3. Abrir no Android Studio

File → Open → selecione o projeto

### 4. Configurar API Keys

No arquivo local.properties:

MAPS_API_KEY=SUACHAVEAQUI
BASE_URL=https://suaapi.com/

### 5. Executar

Clique em Run ▶️

Escolha emulador ou celular físico

## Contribuições da Equipe (Divisão entre 6 Desenvolvedores)
🔵 Rodrigo — Login, Perfil e Notificações

Telas:

Login

Editar Perfil

Notificações

Tipo de Cadastro

Diálogo de Sair

Responsável por:

MVVM de login

MVVM de perfil

MVVM de notificações




🔵 José Victor — Comunicação (Chat, Negociação, Avaliação, Denúncia, Histórico)

Telas:

Negociação

Chat

Avaliação

Denúncia + Diálogo

Histórico de Corridas

Responsável por:

MVVM de negociação

MVVM de chat

MVVM de avaliação

MVVM de histórico

🟢 Cristian Martins — Cadastro do Mototaxista

Telas:

3 etapas de cadastro

Configurações do motorista

Cadastro de veículo

Responsável por:

MVVM completo do fluxo de cadastro do mototaxista

🟢 Luan Henrique — Corridas do Mototaxista

Telas:

Lista de Corridas

Seleção de Corrida

Corrida em andamento

Aguardando início

Caminho no mapa

Entrega e seleção

Responsável por:

MVVM de todas as telas operacionais do mototaxista

🟣 Otavio — Passageiro (Cadastro, Configuração, Escolha)

Telas:

Cadastro do passageiro

Configurações

Escolha do mototaxista

Seleção de local

Responsável por:

MVVM do fluxo completo do passageiro

🟣 Marlom — Passageiro (Corrida, Busca, Menu)

Telas:

Menu principal

Busca de motorista

Criação da corrida

Itinerário (em conjunto com Dev 4)

Responsável por:

MVVM de busca e gerenciamento da corrida do passageiro



## Como o MVVM foi aplicado no projeto
📌 MODEL

Modelos simples representando dados vindos da API.

📌 REPOSITORY

Lida com requisições Retrofit, regras de conversão e comunicação com ViewModel.

📌 VIEWMODEL

Mantém estado da tela, expõe dados, executa regras de interface e navegação.

📌 XML + DataBinding

UI reativa, conectada diretamente aos estados expostos pelo ViewModel.

📌 ACTIVITY / FRAGMENT
