# CUSIP Scraper

This Java application provides the ability to scrape CUSIP data from the http://emma.msrb.org/ website.

Currently, the only data scraped is the following:

- Entity Name
- Entity State
- Initial Offering Price
- Initial Offering Yield

## Pre-requisites

This project is a Maven project, so requires Maven to be installed on your machine.  Maven is a build tool for Java projects that does a lot.  But, fo
r this project, it's used for dependency management.

- [Installing Maven for Windows](http://www.mkyong.com/maven/how-to-install-maven-in-windows/)


## Importing into Eclipse

First start by either cloning this repository or by Downloading the ZIP (in the right-sidebar) and expanding it on your filesystem.

In Eclipse, do the following:

1. In the **File** menu and click **Import**.
2. Under the **Maven** section, select **Existing Maven Projects**.
3. For the **Root Directory**, navigate to where you either cloned the repo or unzipped the download.
4. Finish going through the wizard workflow.  All defaults are fine from there on.

It may take a few moments to import, but it'll eventually show up.


## Running the Project

The main file is the **Main** class in the **cusip** package.  Simply open it up and run it as a Java Application.

The listings of cusips go into a file named **cusip.txt**, which is located in the src/main/resources folder (which you can get to either through Eclipse or on the file system).

After running, the output is dumped into a file named **output.csv** at the root of the project.

## Have questions?

Feel free to contact me at *mikesir87@gmail.com* and let me know!
