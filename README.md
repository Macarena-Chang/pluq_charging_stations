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
<li><a href="">JUnit</a></li>
<li><a href="">Mockito</a></li>
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
2- MeterValues Microservice: Handle Json from a third party APi. Save and Retrieve meter values from DB. <br>
3- Reporting Microservice: Generate Report (Report by city, Report for all locations) <br>
• Report example:
```JSON [
    {
        "city": "Dressrosa",
        "chargingSockets": 2,
        "uids": [
            "PLUQ1006*2",
            "PLUQ1006*1"
        ],
        "totalKwhCharged": 969.7530000000002,
        "sessions": 60,
        "sessionsList": [
            "104328319",
            "104512465",
            "106447864",
            "103404066",
           ...[CONTINUES]
        ],
        "chargedKwhPerSession": {
            "104328319": {
                "totalCharged": 23.588999999999942,
                "physicalReference": "PLUQ1006*1",
                "date": "2023-07-12T16:16:00Z"
            },
            "104512465": {
                "totalCharged": 41.36200000000008,
                "physicalReference": "PLUQ1006*1",
                "date": "2023-07-13T18:31:00Z"
            },
            "106447864": {
                "totalCharged": 35.39300000000003,
                "physicalReference": "PLUQ1006*1",
                "date": "2023-07-26T17:00:00Z"
            },
            "103404066": {
                "totalCharged": 13.043999999999869,
                "physicalReference": "PLUQ1006*1",
                "date": "2023-07-06T07:01:00Z"
            },
           ...[CONTINUES]
        
        },
        "chargedPerSocket": {
            "PLUQ1006*2": 482.31700000000046,
            "PLUQ1006*1": 487.4359999999997
        },
        "chargedPerSocketPerDay": {
            "2023-06-30T22:00:00Z": {
                "PLUQ1006*1": 3.1409999999996217
            },
            "2023-07-01T06:28:00Z": {
                "PLUQ1006*2": 2.556999999999789
            },
            "2023-07-01T06:51:00Z": {
                "PLUQ1006*1": 16.14600000000064
            },
            "2023-07-02T06:20:00Z": {
                "PLUQ1006*2": 12.513000000000375
            },
            "2023-07-02T06:52:00Z": {
                "PLUQ1006*1": 18.600999999999658
            },
            "2023-07-02T09:38:00Z": {
                "PLUQ1006*1": 17.189000000000306
                
            ...[CONTINUES]
            }
        }
    }
]
```
<br>
<br>

## :file_folder: Documentation: Swagger for each service: 
• Locations service: http://localhost:8090/swagger-ui/index.html
![locations](https://github.com/Macarena-Chang/pluq_charging_stations/assets/18247410/ef5facb4-03eb-48e9-a5ee-9ad98dd2b926)<br>
• MeterValues service: http://localhost:8100/swagger-ui/index.html
![mvalues](https://github.com/Macarena-Chang/pluq_charging_stations/assets/18247410/a11ea5ac-37cd-423a-9aa7-8d7b486686ba)<br>
• Report Service: Swagger: http://localhost:8200/swagger-ui/index.html 
![reporting](https://github.com/Macarena-Chang/pluq_charging_stations/assets/18247410/a63fa2ef-0123-429d-b033-8778f944d291)<br>

### :key: Environment Variables
To run this project, you will need to add the following environment variables to your .env file
`{MONGODB_USERNAME}`

`{MONGODB_PASSWORD}`



## :toolbox: Getting Started

### :bangbang: Prerequisites

- JDK 17
- Maven
- Environment Variables


 


### :running: Run Locally

Clone the project

```bash
git clone https://github.com/Macarena-Chang/pluq_charging_stations.git
```

Inside the Project there are 3 microservices, each should run independently
```bash
Configured locally to run in different ports.
```

 

## :handshake: Contact

Macarena Chang - -

Project Link: [https://github.com/Macarena-Chang/pluq_charging_stations](https://github.com/Macarena-Chang/pluq_charging_stations)
