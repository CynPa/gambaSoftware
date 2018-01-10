/*  1:   */ package com.asinfo.as2.enumeraciones;
/*  2:   */ 
/*  3:   */ public enum VariablesMotivosLlamadosAtencion
/*  4:   */ {
/*  5:21 */   FECHA_LLAMADO_ATENCION(":fechaLlamadoAtencion:"),  NOMBRE_EMPRESA(":nombreEmpresa:"),  REPRESENTANTE_LEGAL(":representanteLegal:"),  DIRECCION_EMPRESA(":direccionEmpresa:"),  RUC_EMPRESA(":rucEmpresa:"),  TELEFONO_EMPRESA(":telefonoEmpresa:"),  CIUDAD_EMPRESA(":ciudadEmpresa:"),  NOMBRE_EMPLEADO(":nombreEmpleado:"),  CEDULA_EMPLEADO(":cedulaEmpleado:"),  CARGO_EMPLEADO(":cargoEmpleado:"),  DIRECCION_EMPLEADO(":direccionEmpleado:");
/*  6:   */   
/*  7:   */   private String variable;
/*  8:   */   
/*  9:   */   private VariablesMotivosLlamadosAtencion(String variable)
/* 10:   */   {
/* 11:36 */     this.variable = variable;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getVariable()
/* 15:   */   {
/* 16:44 */     return this.variable;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void setVariable(String variable)
/* 20:   */   {
/* 21:52 */     this.variable = variable;
/* 22:   */   }
/* 23:   */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.enumeraciones.VariablesMotivosLlamadosAtencion
 * JD-Core Version:    0.7.0.1
 */