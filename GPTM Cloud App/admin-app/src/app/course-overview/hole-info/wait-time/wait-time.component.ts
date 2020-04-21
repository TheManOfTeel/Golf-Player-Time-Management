import { Component, OnInit, ViewChild } from '@angular/core';
import { AngularFireDatabase } from '@angular/fire/database';
import { ChartDataSets, ChartOptions } from 'chart.js';
import { Color, BaseChartDirective, Label } from 'ng2-charts';
import * as pluginAnnotations from 'chartjs-plugin-annotation';
import * as firebase from 'firebase/app';
import 'firebase/database';
import 'firebase/auth';

@Component({
  selector: 'app-wait-time',
  templateUrl: './wait-time.component.html',
  styleUrls: ['./wait-time.component.css']
})

export class WaitTimeComponent implements OnInit {

  courseName: any;
  info: any;
  hole18 = false;
  waitTimes: any;
  isLoading = false;

  @ViewChild(BaseChartDirective) chart: BaseChartDirective;

  // Init the graph
  public lineChartData: ChartDataSets[] = [
    { data: [], label: 'Minutes' },
  ];
  public queueData: ChartDataSets[] = [
    {data: [], label: 'Groups'}
  ];
  public lineChartLabels: Label[] = [];
  public queueLabels: Label[] = [];
  public lineChartOptions: (ChartOptions & { annotation: any }) = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
      // We use this empty structure as a placeholder for dynamic theming.
      xAxes: [{}],
      yAxes: [
        {
          id: 'y-axis-0',
          position: 'left',
          ticks: {
            beginAtZero: true
          }
        },
      ]
    },
    annotation: {
      annotations: [
        { },
      ],
    },
  };
  public lineChartColors: Color[] = [
    { // red
      backgroundColor: 'rgba(255,0,0,0.3)',
      borderColor: 'red',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: '#fff',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    }
  ];
  public lineChartLegend = true;
  public lineChartType = 'line';
  public lineChartPlugins = [pluginAnnotations];

  constructor(public db: AngularFireDatabase) { }

  getCourseName() {
    const userId = firebase.auth().currentUser.uid;
    return firebase.database().ref('/Users/' + userId).once('value').then(function(snapshot) {
      const golfCourse = (snapshot.val() && snapshot.val().golfCourse || 'No Associated Course');
      return golfCourse;
    });
  }

  // Read the number of holes
  getCourseDetails(courseName) {
    // use the golfCourse value to match it to the GolfCourse table and get the hole info
    return firebase.database().ref('/GolfCourse/' + courseName + '/Holes').once('value').then(function(snapshot) {
      // All the data is being pulled here. Assign it a value then it can be shown in the front end.
      const data = snapshot.val();
      return data;
    });
  }

  ngOnInit(): void {
    this.isLoading = true;
    this.getCourseName()
    .then(val => {
      this.courseName = val;
      this.getCourseDetails(this.courseName)
      .then(data => {
        this.info = data;
        setTimeout(() => {
          if (this.info.Hole18 != null) {
            this.hole18 = true;
  
            // Add the x axis labels so that the data can be updated on changes
            this.lineChartLabels.push('Hole 1', 'Hole 2', 'Hole 3', 'Hole 4', 'Hole 5', 'Hole 6', 'Hole 7', 'Hole 8',
            'Hole 9', 'Hole 10', 'Hole 11', 'Hole 12', 'Hole 13', 'Hole 14', 'Hole 15', 'Hole 16', 'Hole 17', 'Hole 18');
            this.queueLabels.push('Hole 1', 'Hole 2', 'Hole 3', 'Hole 4', 'Hole 5', 'Hole 6', 'Hole 7', 'Hole 8',
            'Hole 9', 'Hole 10', 'Hole 11', 'Hole 12', 'Hole 13', 'Hole 14', 'Hole 15', 'Hole 16', 'Hole 17', 'Hole 18');
  
            // Read from firebase
            this.db.list('GolfCourse/' + this.courseName + '/WaitTimes').valueChanges().subscribe(res => {
              this.waitTimes = res;
  
              // Plot points
              this.lineChartData = [
                { data: [this.waitTimes[0].WaitTime, this.waitTimes[10].WaitTime, this.waitTimes[11].WaitTime,
                  this.waitTimes[12].WaitTime, this.waitTimes[13].WaitTime, this.waitTimes[14].WaitTime, this.waitTimes[15].WaitTime,
                  this.waitTimes[16].WaitTime, this.waitTimes[17].WaitTime, this.waitTimes[1].WaitTime, this.waitTimes[2].WaitTime,
                  this.waitTimes[3].WaitTime, this.waitTimes[4].WaitTime, this.waitTimes[5].WaitTime, this.waitTimes[6].WaitTime,
                  this.waitTimes[7].WaitTime, this.waitTimes[8].WaitTime, this.waitTimes[9].WaitTime],
                  label: 'Minutes' }
              ];
  
              // For queue table
              this.queueData = [
                { data: [this.waitTimes[0].Queue, this.waitTimes[10].Queue, this.waitTimes[11].Queue,
                  this.waitTimes[12].Queue, this.waitTimes[13].Queue, this.waitTimes[14].Queue, this.waitTimes[15].Queue,
                  this.waitTimes[16].Queue, this.waitTimes[17].Queue, this.waitTimes[1].Queue, this.waitTimes[2].Queue,
                  this.waitTimes[3].Queue, this.waitTimes[4].Queue, this.waitTimes[5].Queue, this.waitTimes[6].Queue,
                  this.waitTimes[7].Queue, this.waitTimes[8].Queue, this.waitTimes[9].Queue] }
              ];
  
              this.isLoading = false;
            });
          }
          if (!this.info.Hole18) {
             // Add the x axis labels so that the data can be updated on changes
            this.lineChartLabels.push('Hole 1', 'Hole 2', 'Hole 3', 'Hole 4', 'Hole 5', 'Hole 6', 'Hole 7', 'Hole 8',
            'Hole 9');
            this.queueLabels.push('Hole 1', 'Hole 2', 'Hole 3', 'Hole 4', 'Hole 5', 'Hole 6', 'Hole 7', 'Hole 8',
            'Hole 9');
  
            // Read from firebase
            this.db.list('GolfCourse/' + this.courseName + '/WaitTimes').valueChanges().subscribe(res => {
              this.waitTimes = res;
  
              // Plot points
              this.lineChartData = [
                { data: [this.waitTimes[0].WaitTime, this.waitTimes[1].WaitTime,
                  this.waitTimes[2].WaitTime, this.waitTimes[3].WaitTime, this.waitTimes[4].WaitTime,
                  this.waitTimes[5].WaitTime, this.waitTimes[6].WaitTime, this.waitTimes[7].WaitTime,
                  this.waitTimes[8].WaitTime],
                  label: 'Minutes' }
              ];
  
              // For queue table
              this.queueData = [
                { data: [this.waitTimes[0].Queue, this.waitTimes[1].Queue, this.waitTimes[2].Queue,
                  this.waitTimes[3].Queue, this.waitTimes[4].Queue, this.waitTimes[5].Queue, this.waitTimes[6].Queue,
                  this.waitTimes[7].Queue, this.waitTimes[8].Queue] }
              ];
  
              this.isLoading = false;
            });
          }
        }, 2000);
      });
    });
  }
}
