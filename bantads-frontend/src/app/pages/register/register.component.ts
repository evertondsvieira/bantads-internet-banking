import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, FormsModule, NgForm, NgModel, Validators } from '@angular/forms';
import { User } from '../../models/user.model';
import { Router } from '@angular/router';
import { CurrencyPipe } from "@angular/common";
import { NgbModal } from "@ng-bootstrap/ng-bootstrap";
import { NumericoDirective } from '../../modules/shared/directives/numerico.directive';
import { AuthService } from '../../services/auth/auth.service';

import { ManagerService } from '../../services/manager/manager.service';
import { Manager } from '../../models/manager.model';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
  viewProviders: [NumericoDirective, CurrencyPipe]
})
export class RegisterComponent {
  @ViewChild('formRegistro') formRegistro!: NgForm;
  @ViewChild('accountConfirmationModal') accountConfirmationModal: any;

  user?: User = new User();
  logradouro: string | null = null;
  cidade: any;
  cep: number | null = null;
  estado: string | null = null;
  formattedSalary: string | null;
  salary: number;
  complemento: string | null = null;
  numero: string | null = null;
  cpf: string | null = null;
  telefone: string | null = null;
  private modalRef: any;

  constructor(
    private router: Router,
    private currencyPipe: CurrencyPipe,
    private modalService: NgbModal,
    private authService: AuthService ,
  ) {
    this.formattedSalary = 'R$ 0,00';
    this.salary = 0;
  }

  openConfirmationModal() {
    this.modalRef = this.modalService.open(this.accountConfirmationModal, {ariaLabelledBy: 'accountConfirmationModal'});
    this.modalRef.result.then((result: string) => {
      if (result === 'confirm') {
        this.submit();
      }
    }, (reason: any) => {
    });
  }


  submit() {
    this.router.navigate(['/login']); /*Levar para página de login: Remover quando for refatorar*/
    console.log('ops');
    
    if (this.formRegistro) {
      let cadastro = this.formRegistro.value;
      cadastro.salary = this.salary;
      cadastro.cep = cadastro.cep.replace(/\D/g, '');
      cadastro.logradouro = this.logradouro;
      cadastro.cidade = this.cidade;
      cadastro.complemento = this.complemento;
      cadastro.numero = this.numero;
      
      /* Lucas Cazionato | 07/04/2024-05:43 | Projeto nao estava compilando entao deixei como comentario apenas para rodar
      this.authService.register(cadastro).subscribe(
        (response: any) => {
          console.log('Usuário registrado com sucesso:', response);
          if (this.modalRef) {
            this.modalRef.close();
          }
          alert('Cadastro realizado com sucesso.' +
            'Aguarde a confirmação por e-mail.');
          this.router.navigate(['/login']);
        },
        (error: any) => {
          console.error('Erro ao registrar usuário:', error);
        },
      );
      */
    } else {
      console.error('formRegistro is not defined');
    }
  }
}

