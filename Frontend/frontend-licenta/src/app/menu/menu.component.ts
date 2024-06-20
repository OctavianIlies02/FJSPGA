import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [MenuComponent],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent implements OnInit {
  activeUrl: string;

  constructor(private router: Router) {
    this.activeUrl = '';
  }

  ngOnInit(): void {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.activeUrl = event.urlAfterRedirects;
      }
    });
  }

  isActive(url: string): boolean {
    return this.activeUrl === url;
  }
}
