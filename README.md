# ⛑️ RescueMission 

## 👨🏽‍💻 Development Process

The complete Agile software development life cycle was followed for this project. This meant an incremental and iterative approach for gradual release of value. From gathering requirements, to brainstorming, to designing a walking skeleton, developing an MVP, gathering feedback from experienced individuals in the field, to testing, and deployment. This is the result of 2 months of iterative and incremental development.

## Overview

RescueMission is a Java-based project developed as part of an assignment for the SFWRENG 2AA4 course at McMaster University. The project involves creating a command center for a rescue drone to explore an island, locate stranded individuals, and identify suitable rescue points.

This product is an _exploration command center_ for the [Island](https://ace-design.github.io/island/) serious game. 

## 📝 Project Description

You have been tasked by ACME Corp to develop a control system for their rescue drone, designed to find survivors on isolated islands and determine the best locations for rescue teams to land. The drone operates in a marine environment and must navigate hostile terrain while managing its battery, radio range, and command integrity.

The drone is controlled through commands sent to it, which include:

### ⭐️ Key Objectives

1. **Locate the Island**: Use radar and photo scanners to find the island.
2. **Find Emergency Sites**: Identify the location of emergency sites where survivors are sheltering.
3. **Locate Creeks**: Determine suitable inlets on the island's coast for rescue boats to land.
4. **Optimize Commands**: Ensure efficient use of the drone's battery and maintain control integrity.

## 🎮 Drone Control Commands

- **Fly**: Move the drone forward.
- **Heading**: Change the drone's direction.
- **Radar**: Use sensors to detect terrain.
- **Photo Scanner**: Identify biomes and points of interest.
- **Stop**: End the mission and return to base.

## 💻 Technical Environment

The project uses the following technologies and tools:
- **Java**: Core programming language.
- **Maven**: For project management and build automation.
- **JSON**: For command communication with the drone.
- **JUnit**: Testing framework used.
- **UML**: For modeling the system design.

## Product Demonstration
Click the image below for a video demonstration of this product.

[![Rescue Mission Visualizer](https://github.com/AHMEDELZARIA/LLVM-evolution/assets/93144563/7ce7b38d-1908-4e83-b2fa-f4348d8ecc7b)](https://mcmasteru365.sharepoint.com/sites/course-549805-group/_layouts/15/stream.aspx?id=%2Fsites%2Fcourse%2D549805%2Dgroup%2FShared%20Documents%2FGeneral%2F204%5Fexploration%2Emov&referrer=StreamWebApp%2EWeb&referrerScenario=AddressBarCopied%2Eview%2E803a8caf%2D4a76%2D42c7%2D9c1f%2D6f561082929f)

## How to Compile

To compile the project, use Maven:
```bash
mvn clean package
```

## How to Run

To execute the command center with a specific map, use:
```bash
mvn exec:java -q -Dexec.args="./maps/map03.json"
```

This will generate output files in the `outputs` directory:
- `_pois.json`
- `Explorer_Island.json`
- `Explorer.svg`

## Deliverables

1. **Code**: Main branch on the GitHub repository.
2. **Kanban Board**: Project management.
3. **PDF Report**: Detailed software design and implementation report.

## Repository Structure

1. UML/: Contains UML diagrams for the project.
2. maps/: Includes JSON files of different maps used for testing.
3. src/: Source code directory.

## Deploying the project to the arena

Each week, we had to `tag` the version we wanted to submit for the competition. This version would be used in the weekly run. No tag means no competition.

The tag syntax is `wX`, with `X` the week number. So our product for the first week would be tagged `w1`.

## License

This project is licensed under the MIT License. See the LICENSE.txt file for details.
