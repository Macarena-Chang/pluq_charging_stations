<div align='center'>

<h1>Pluq Solutions</h1>

<p>API Requirements 
• Operations to save and retrieve locations from the database. 
• Operations to save and retrieve meter values from the database. 
• Operations to generate a charging report showing per location:</p>

 


</div>

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=macarena-chang_pluq_charging_stations&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=macarena-chang_pluq_charging_stations) ![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/macarena-chang/pluq_charging_stations/maven.yml)

## :star2: About the Project
### :space_invader: Tech Stack
<li><a href="">Java 17</a></li>
<li><a href="">SpringBoot</a></li>
<li><a href="">Maven</a></li>
<li><a href="">Microservices</a></li>
<li><a href="">RESTful APIs</a></li>
<li><a href="">Jenkins/Github Actions</a></li>
<li><a href="">SonarCLoud</a></li>
<li><a href="">Swagger/OpenAPI</a></li>
</ul> </details>
<li><a href="">MongoDB</a></li>
<li><a href="">Cloud Database Instance</a></li>
</ul> </details>
<li><a href="">CI: Automate builds and testing</a></li>
</ul> </details>

### :dart: Features
- Quality Analysis
- SonarCloud Integration 
- Microservice architecture

## Microservices
1- Location Microservice: Save and retrieve Locations from DB. <br> 
2- MeterValues Microservice: Handle Json from a third party APi. Should Save and Retrieve meter values from DB. <br>
3- Reporting Microservice: Generate Report  <br>
 
![locations](https://github.com/Macarena-Chang/pluq_charging_stations/assets/18247410/79ee640d-c01a-467c-9f87-9433d69b4355)<br>
![mvalues](https://github.com/Macarena-Chang/pluq_charging_stations/assets/18247410/b8ebd0fb-eabb-44f5-978d-8307f6bacd58)<br>
![reporting](https://github.com/Macarena-Chang/pluq_charging_stations/assets/18247410/afd3e976-257c-4c44-adbf-54f84f79ee81)<br>


### :key: Environment Variables
To run this project, you will need to add the following environment variables to your .env file
`{MONGODB_USERNAME}`

`{MONGODB_PASSWORD}`



## :toolbox: Getting Started

### :bangbang: Prerequisites

- JDK 17
- Maven
- Environment Variables


### :gear: Installation

Clone Repository
```bash
git clone https://github.com/Macarena-Chang/pluq_charging_stations.git
```


### :running: Run Locally

Clone the project

```bash
https://github.com/Macarena-Chang/pluq_charging_stations
```
Inside the Project there are 3 microservices, each should run independently
```bash
Configured locally to run in different ports.
```


## :compass: Roadmap

* [ ] Complete Reporting Microservice
* [ ] Unit Testing (JUnit - Mockito)
* [ ] Include API Gateway
* [ ] Deploy Microservices
* [ ] Exception handling!!


## :handshake: Contact

Macarena Chang - -

Project Link: [https://github.com/Macarena-Chang/pluq_charging_stations](https://github.com/Macarena-Chang/pluq_charging_stations)
