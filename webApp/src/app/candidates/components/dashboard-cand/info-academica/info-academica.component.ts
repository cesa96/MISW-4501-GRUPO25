import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { InfAcademicaService } from 'src/app/candidates/services/inf-academica.service';

@Component({
  selector: 'app-info-academica',
  templateUrl: './info-academica.component.html',
  styleUrls: ['./info-academica.component.css']
})
export class InfoAcademicaComponent {
  displayedColumns: string[] = ['id', 'tittle', 'institution', 'beginDate', 'endDate', 'studyType', 'actions']
  dataSource = new MatTableDataSource<any>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private infAcademicaService: InfAcademicaService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.infAcademicaService.listInfoAcademica(1)
    .subscribe({
      next: data => {
        this.dataSource = new MatTableDataSource(data);
        return data;
      },
      error: error => console.log(error),
      complete: () => {
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      }
    })
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  redirectCreateInfoAcad(){
    this.router.navigate(['candidato/dashboard/1/add-info-academica'])
  }
}
