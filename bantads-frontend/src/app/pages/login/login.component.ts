import { Component, OnInit, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Auth } from '../../models/auth.model';
import { AuthService } from '../../services/auth.service';
import { User } from '../../models/user.model';
import { CommonModule } from '@angular/common';
import {HttpClientModule} from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, HttpClientModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  @ViewChild('formLogin') formLogin! : NgForm;
  public auth: Auth = new Auth();
  public loading: boolean = false;
  public message!: string;
  public passwordVisible: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) {  
    this.verifyAuth();
  } 

  ngOnInit(): void {
    this.verifyAuth();
    this.route.queryParams.subscribe(param => this.message = param["error"])
  }

  login(): void{
    console.log(JSON.stringify(this.auth));
    
    this.loading = true;
    if(this.formLogin.form.valid){
      
      this.authService.login(this.auth).subscribe(authUser => {
        let user: User | null = authUser ? authUser : null;
        
        if(user != null){
          this.authService.loggedUser = user;   
          this.loading = false;

          if(user.profile == 'CLIENT'){
            this.router.navigate(["/home"]);
          }
          else if (user.profile == 'MANAGER'){
            this.router.navigate(['/manager/home']);
          }
          else if(user.profile == 'ADMIN'){
            this.router.navigate(['/admin/home']);
          }
        }
        else{
          this.message = "Usuário/Senha Inválidos";
        }
      })
    }
    this.loading = false;
  }

  private verifyAuth(){
    let loggedUser = this.authService.loggedUser;
    if(loggedUser){
      if(loggedUser.profile == 'CLIENT'){
        this.router.navigate(["/home"]);
      }
      else if(loggedUser.profile == 'MANAGER'){
        this.router.navigate(['/manager/home']);
      }
      else if(loggedUser.profile == 'ADMIN'){
        this.router.navigate(['/admin/home']);
      }
    }
  }
  togglePassword(): void{
    this.passwordVisible = !this.passwordVisible;
  }
}