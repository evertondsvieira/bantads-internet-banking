import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Auth } from '../../models/auth.model';
import { AuthService } from '../../services/auth/auth.service';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  @ViewChild('formLogin') formLogin!: NgForm;
  public auth: Auth = new Auth();
  public loading: boolean = false;
  public errorMessage: string = ''; // Nova propriedade para armazenar a mensagem de erro
  public passwordVisible: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(param => this.errorMessage = param["error"]);
  }

  login(): void {
    this.loading = true;
    
    if (this.formLogin.form.valid) {
      this.authService.login(this.auth).subscribe(authUser => {
        let user: User | null = authUser ? authUser : null;
        
        // Remover quando não tiver o json-server
        user = Array.isArray(user) ? user[0] : user;

        if (user != null) {
          this.authService.loggedUser = user;   
          this.loading = false;

          if (user.profile == 'CLIENT') {
            this.router.navigate(["/home"]);
          } else if (user.profile == 'MANAGER') {
            this.router.navigate(['/manager/home']);
          } else if (user.profile == 'ADMIN') {
            this.router.navigate(['/admin/home']);
          }
        } else {
          this.errorMessage = "Email/Senha Inválidos"; // Define a mensagem de erro
          this.loading = false;
        }
      });
    } else {
      this.loading = false;
    }
  }

  togglePassword(): void {
    this.passwordVisible = !this.passwordVisible;
  }
}