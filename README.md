# Index: 
**Git repositories**
1. [Git repositories](#git-repositories)

**Installation**
1. [Install building tools](#install-building-tools)
2. [Download Project](#download-project)
3. [Z3 Solver](#z3-solver)
4. [Intellij - RUN PROGRAM](#intellij---run-program)
   - [Confirm the AntidoteSQL Commands](#confirm-the-antidotesql-commands)
   - [Generate the VeriFx code](#generate-the-verifx-code)
   - [Run the Proofs](#run-the-proofs)
   - [Configure Intellij to recognize veriFx and Antidote SQL files](#configure-intellij-to-recognize-verifx-and-antidote-sql-files)

**Antidote SQL Commands rules**
1. [Conditions for commands (currently implemented)](#conditions-for-commands-currently-implemented)
2. [Create Table:](#create-table)
3. [Data Types](#data-types)
4. [Invariant Options](#invariant-options)
    - [Primary Key](#primary-key)
    - [Foreign Key](#foreign-key)






<br><br><br><br>#######################################################################################
# Git repositories: 
Main repository with all CRDTs:    
    https://github.com/verifx-prover/verifx

This repository with automated table systems:
    https://github.com/fmssilva/automate_verifx




<br><br><br><br>#######################################################################################
# Installation

## Install building tools
   - **example for Ubuntu in some VM or in WSL on Windows run:**


           sudo apt update
           sudo apt upgrade
           sudo apt install binutils    //to have utils like archive tool (ar), etc.
           sudo apt install g++         //C++ compiler
           sudo apt install make
           sudo apt install python3
           sudo apt install openjdk-11-jdk    //instal jdk 11 and not the most recent version
           java --version // to confirm the jdk default jdk version
   
   - **example for Windows:**     
       - download and install "Build Tools for Visual Studio": 
         - na instalação selecionar c++ e build tools
       - download and install python 3 
       - download and install jdk 11 (or install after through intellij)
     

## Download Project
   - inside ubuntu, or inside some folder in WSL, or directly in Windows: 
   
         git clone https://github.com/fmssilva/automate_verifx

## Z3 Solver
  - We use the Z3 solver to run the proofs 
  - download the most up-to-date version available in assets in:
    - https://github.com/Z3Prover/z3/releases
  - download src code, extract to some folder, "cd" to that Z3 folder
  - compile:
      - python3 scripts/mk_make.py -x --java
        - -x to create 64 bits linking files
          - if with -x it gives error, just install the Z3 without it, with 32 bits. Then when running the proofs in intellij it will give an error:  "Can't load IA 32-bit .dll on a AMD 64-bit platform", so then go to the Z3 again in git and download the corresponding version ex: "z3-4.13.0-x64-win.zip", and then just copy the files libz3.dll/.dylib/.so e libz3java.dll/.dylib/.so from z3/bin to the root folder of the project
        - --java to create the lib3java.dll/.dylib/.so  file
      - cd build
      - make // on linux
        - nmake // windows » open a terminal for "Developer Command Prompt" (this is not a normal terminal, nor even the administrator mode terminal. you should search for it in windows); that terminal will open normally in "C:\Program Files (x86)\Microsoft Visual Studio\2022\BuildTools>" so then do cd to the folder you have the Z3 
      - confirm in build if the files lib3 and lib3java were produced and copy them to the root folder of the project (replace the existing files there - and each SO will have different linking files like .so, .dll, .lib, etc. )
      - in linux, at the end of make there should be the warning: Z3Py scripts can already be executed in the 'build/python' directory. Z3Py scripts stored in arbitrary directories can be executed if the 'build/python' directory is added to the PYTHONPATH environment variable and the 'build' directory is added to the LD_LIBRARY_PATH environment variable.
        - just install Z3 globally with:
          - sudo make install 
  - more on Z3 installation in:
    - https://github.com/Z3Prover/z3


## Intellij - RUN PROGRAM
- open project
  - if in WSL: close current project to go to initial pannel; click Remote dev » WSL; open project from the folder in WSL


- **Confirm the AntidoteSQL Commands**
    - In Folder: AntidoteSQL_Data, in file input.txt
      write the commands to create tables
    - In READ_ME_AntidoteSQL find the currently implemented rules for the commands


- **Generate the VeriFx code**
    - delete the folders:
        - generatedSysTables in src/main/verifx
        - generatedSysTablesProofs in src/test/scala
    - run the program in AntidoteSQL_To_VeriFx in src/main/scala: 
        - the program will create the deleted packages again, now for the given AntidoteSQL input
        - first time opening a scala doc it will ask to install the scala and sbt builder
          - sbt - Simple Build Tool - is the building tool for scala projects, like maven or gradle... 
    - check if the classes were generated correctly:
        - in src/main/verifx/generatedSysTables, check if the classes were created for the tables given in input.txt
        - in src/test/scala, check if the proofs were created for the tables given in input.txt

- **Run the Proofs**
    - open the files in src/test/scala/generatedSysTablesProofs and run the proofs
      - start with the first proof "is_a_CvRDT" to check if the Z3 works
    - if errors because of the version of jdk, so select the version 11:
          - file » proj strucute » sdk » choose jdk 11 
            - (if working remotely on WSL so chose the jdk 11 installed there)
  


- **Configure Intellij to recognize veriFx and Antidote SQL files**
  - Go to settigns » Editor » File Types
  
  - **Verifx files:** 
    - In Recognized File Types add a new type for verifx
        - step 1 of the image:
          - <img src="readme_images%2Fverifx_in_intellij.png" alt="Verifx in IntelliJ" width="500">


          
    - In the options write:
      - <img src="readme_images%2Fverifx_options.png" alt="Verifx Options" width="400">

    
    - Don´t click in ignore case 
    - In keywords add, for example: 
  
      - 1: (main commands)

              import
              class
              object
              trait
              proof
              enum
              extends
              override
              private
              protected
              def
              val
              new
              =
              {
              }
        

      - 2: (types)

              this
              
              String
              Int
              Boolean
              Unit
              
              LWWRegister
              FWWRegister
              MVRegister
              EWFlag
              
              GCounter
              PNCounter
              PNCounter2
              BCounterGeq
              BCounterLeq
              
              CvRDT
              CvRDTProof
              CvRDTProof1
              CvRDTProof2
              
              LamportClock
              VersionVector
              
              UWTable
              
              Tuple
              Vector
              LList
              Set
              Map

      - 3: (operators...)

              (
              )
              [
              ]
              :
              ;
              &&
              ||
              !
              ==
              >=
              <=
              >
              <

      
      - 4: (more commands)

              @recursive
              if
              else
              for
              while
              yield
              match
              case
              forall
              exists
              =>
              true
              false


    - Click ok
    - Select the verifx language and add the new extension of file to it (step 2 of the image above)


  - **Antidote SQL files:**
    - Same steps as to the verifx language described above
    - In the options write:
      - <img src="readme_images%2Fantidote_options.png" alt="Antidote Options" width="400">
 

    - **Click - ignore case**
    - In key words add, for example:
    
      - 1: (main commands)

              CREATE
              ALTER
              DROP
              TABLE
              VIEW
              INDEX
              
              SELECT
              INSERT
              UPDATE
              DELETE
              INTO
              VALUES
              SET
              FROM
              WHERE
              GROUP BY
              HAVING
              ORDER BY
              LIMIT
              ASC
              DESC


      - 2: (data types)

              VARCHAR
              INTEGER
              INT 
              BOOLEAN 
              COUNTER_INT


      - 3: (invariants  & operators)
              
              PRIMARY 
              FOREIGN 
              REFERENCES  
              KEY
              CHECK 
              UNIQUE
              NULL
              IS
              (
              )
              [
              ]
              ;
              :
              AND
              OR
              NOT
              ==
              >=
              <=
              >
              <
              IN
              LIKE
              BETWEEN


      - 4: (update Policies)
              
              UPDATE-WINS
              DELETE-WINS
              NO CONCURRENCY
              
              LWW 
              MULTI-VALUE
              ADDITIVE 
              NO CONCURRENCY










<br><br><br><br>#######################################################################################

# Antidote SQL Commands rules 

## Conditions for commands (currently implemented)
###### 1 instruction == 1 line 
    ex don't divide the specification of an attribute in multiple lines
        CREATE UPDATE-WINS TABLE Album (
            ...
            art_artist VARCHAR 
            foreign key update-wins references 
            Art(art_name),
            ...
        )
###### Command end with ")" IN A SEPARATE LINE
    ex:
        CREATE UPDATE-WINS TABLE Album (
            attrib1 ...
        )
    and not
        CREATE UPDATE-WINS TABLE Album (
            attrib1 ...   )
    
###### Syntax is checked just locally
    ex we can have an attribute with both checks and FK, and the order doesn't matter, 
        can be first checks or FK or vice versa
    but to specify a FK or checks we must follow the rules described bellow 

###### Comments
    //      - single line comments
    /* */   - multi line comments

# Create createTable.Table: 
## Attributes: 
###### Minimum 2 attributes in the table 
     CREATE UPDATE-WINS TABLE Art (
        art_name VARCHAR PRIMARY KEY
        country LWW VARCHAR
    )
###### specify attribute with: <br> { name }  { data type }  { update policy | default == NO_CONCURRENCY }  { invariant options }
    CREATE UPDATE-WINS TABLE Art (
        art_name VARCHAR PRIMARY KEY
        country LWW VARCHAR
    )
    CREATE UPDATE-WINS TABLE Alb(
        title VARCHAR PRIMARY KEY,
        art_artist VARCHAR foreign key update-wins references Art(art_name),
        year LWW INT check(year >= 1900 AND year <= 2022),
        price LWW INT check(price >= 0 AND price <= 10000),
    )

## Data Types
###### Extra Specifications are not handled yet
    CREATE TABLE Album (
        name VARCHAR(100),
        ...
    );

## Invariant Options
### Primary Key:
###### Can be written after each attribute, or in a separate line, AFTER the attributes:
    CREATE TABLE Alb (
        name VARCHAR PRIMARY KEY,
        country VARCHAR PRIMARY KEY,
        style VARCHAR PRIMARY KEY,
    );

    CREATE TABLE Album (
        name VARCHAR,
        country VARCHAR,
        style VARCHAR,
        PRIMARY KEY (name, country, style)
    );

### Foreign Key:
###### AQL supports references only to primary keys
###### FK attribute must have same data type of the PK in the referenced table 
###### FK invariant must be set for each attribute individually
    GOOD example:
        CREATE TABLE Artist (
            art_name VARCHAR FOREIGN KEY update-wins references Artist(name),
            art_country VARCHAR FOREIGN KEY update-wins references Artist(country),
            ...
        );

    WRONG example:
        CREATE TABLE Artist (
            art_name VARCHAR,
            art_country VARCHAR,
            FOREIGN KEY update-wins references Artist(name, country)
        );

###### If a table references other which has multiple PK, so all of those PK must be referenced 
    ex - Artist must reference the 2 PK of the Album table 
        CREATE TABLE Album (
            name VARCHAR PRIMARY KEY,
            country VARCHAR PRIMARY KEY,
        )
        CREATE TABLE Artist (
            art_name VARCHAR FOREIGN KEY update-wins references Artist(name),
            country VARCHAR FOREIGN KEY update-wins references Artist(country),
            ...
        )



      
