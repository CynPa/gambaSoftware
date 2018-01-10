/*   1:    */ package com.asinfo.as2.entities.vista;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Empleado;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.Id;
/*   8:    */ import javax.persistence.JoinColumn;
/*   9:    */ import javax.persistence.ManyToOne;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ 
/*  12:    */ @Entity
/*  13:    */ @Table(name="v_informacion_empleado")
/*  14:    */ public class VInformacionEmpleado
/*  15:    */ {
/*  16:    */   @Id
/*  17:    */   @Column(name="id")
/*  18:    */   private int id;
/*  19:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  20:    */   @JoinColumn(name="id_empleado")
/*  21:    */   private Empleado empleado;
/*  22:    */   @Column(name="apellidos")
/*  23:    */   private String apellidos;
/*  24:    */   @Column(name="nombres")
/*  25:    */   private String nombres;
/*  26:    */   @Column(name="cedula")
/*  27:    */   private String cedula;
/*  28:    */   @Column(name="calle_principal")
/*  29:    */   private String callePrincipal;
/*  30:    */   @Column(name="numero_casa")
/*  31:    */   private String numeroCasa;
/*  32:    */   @Column(name="ciudad")
/*  33:    */   private String ciudad;
/*  34:    */   @Column(name="provincia")
/*  35:    */   private String provincia;
/*  36:    */   @Column(name="telefono")
/*  37:    */   private String telefono;
/*  38:    */   @Column(name="establecimiento")
/*  39:    */   private String establecimiento;
/*  40:    */   @Column(name="residencia_trabajador")
/*  41:    */   private String residenciaTrabajador;
/*  42:    */   @Column(name="pais_residencia")
/*  43:    */   private String paisResidencia;
/*  44:    */   @Column(name="aplica_convenio")
/*  45:    */   private String aplicaConvenio;
/*  46:    */   @Column(name="tipo_trabajador_discapacidad")
/*  47:    */   private String tipoTrabajadorDiscapacidad;
/*  48:    */   @Column(name="porcentaje_discapacidad")
/*  49:    */   private String porcentajeDiscapacidad;
/*  50:    */   @Column(name="tipo_identificacion_discapacidad")
/*  51:    */   private String tipoIdentificacionDiscapacidad;
/*  52:    */   @Column(name="identificacion_discapacidad")
/*  53:    */   private String identificacionDiscapacidad;
/*  54:    */   @Column(name="tipo_identificacion")
/*  55:    */   private String tipoIdentificacion;
/*  56:    */   
/*  57:    */   public Empleado getEmpleado()
/*  58:    */   {
/*  59: 99 */     return this.empleado;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setEmpleado(Empleado empleado)
/*  63:    */   {
/*  64:109 */     this.empleado = empleado;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public String getCedula()
/*  68:    */   {
/*  69:118 */     return this.cedula;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setCedula(String cedula)
/*  73:    */   {
/*  74:128 */     this.cedula = cedula;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getCallePrincipal()
/*  78:    */   {
/*  79:137 */     return this.callePrincipal;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setCallePrincipal(String callePrincipal)
/*  83:    */   {
/*  84:147 */     this.callePrincipal = callePrincipal;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String getNumeroCasa()
/*  88:    */   {
/*  89:156 */     return this.numeroCasa;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setNumeroCasa(String numeroCasa)
/*  93:    */   {
/*  94:166 */     this.numeroCasa = numeroCasa;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public String getCiudad()
/*  98:    */   {
/*  99:175 */     return this.ciudad;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setCiudad(String ciudad)
/* 103:    */   {
/* 104:185 */     this.ciudad = ciudad;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public String getProvincia()
/* 108:    */   {
/* 109:194 */     return this.provincia;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setProvincia(String provincia)
/* 113:    */   {
/* 114:204 */     this.provincia = provincia;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public String getTelefono()
/* 118:    */   {
/* 119:213 */     return this.telefono;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setTelefono(String telefono)
/* 123:    */   {
/* 124:223 */     this.telefono = telefono;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public int getId()
/* 128:    */   {
/* 129:232 */     return this.id;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String getApellidos()
/* 133:    */   {
/* 134:241 */     return this.apellidos;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setApellidos(String apellidos)
/* 138:    */   {
/* 139:251 */     this.apellidos = apellidos;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String getNombres()
/* 143:    */   {
/* 144:260 */     return this.nombres;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setNombres(String nombres)
/* 148:    */   {
/* 149:270 */     this.nombres = nombres;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setId(int id)
/* 153:    */   {
/* 154:280 */     this.id = id;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String getEstablecimiento()
/* 158:    */   {
/* 159:289 */     return this.establecimiento;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setEstablecimiento(String establecimiento)
/* 163:    */   {
/* 164:299 */     this.establecimiento = establecimiento;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public String getResidenciaTrabajador()
/* 168:    */   {
/* 169:308 */     return this.residenciaTrabajador;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setResidenciaTrabajador(String residenciaTrabajador)
/* 173:    */   {
/* 174:318 */     this.residenciaTrabajador = residenciaTrabajador;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public String getPaisResidencia()
/* 178:    */   {
/* 179:327 */     return this.paisResidencia;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setPaisResidencia(String paisResidencia)
/* 183:    */   {
/* 184:337 */     this.paisResidencia = paisResidencia;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public String getAplicaConvenio()
/* 188:    */   {
/* 189:346 */     return this.aplicaConvenio;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setAplicaConvenio(String aplicaConvenio)
/* 193:    */   {
/* 194:356 */     this.aplicaConvenio = aplicaConvenio;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public String getTipoTrabajadorDiscapacidad()
/* 198:    */   {
/* 199:365 */     return this.tipoTrabajadorDiscapacidad;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setTipoTrabajadorDiscapacidad(String tipoTrabajadorDiscapacidad)
/* 203:    */   {
/* 204:375 */     this.tipoTrabajadorDiscapacidad = tipoTrabajadorDiscapacidad;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String getPorcentajeDiscapacidad()
/* 208:    */   {
/* 209:384 */     return this.porcentajeDiscapacidad;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setPorcentajeDiscapacidad(String porcentajeDiscapacidad)
/* 213:    */   {
/* 214:394 */     this.porcentajeDiscapacidad = porcentajeDiscapacidad;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public String getTipoIdentificacionDiscapacidad()
/* 218:    */   {
/* 219:403 */     return this.tipoIdentificacionDiscapacidad;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setTipoIdentificacionDiscapacidad(String tipoIdentificacionDiscapacidad)
/* 223:    */   {
/* 224:413 */     this.tipoIdentificacionDiscapacidad = tipoIdentificacionDiscapacidad;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public String getIdentificacionDiscapacidad()
/* 228:    */   {
/* 229:422 */     return this.identificacionDiscapacidad;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void setIdentificacionDiscapacidad(String identificacionDiscapacidad)
/* 233:    */   {
/* 234:432 */     this.identificacionDiscapacidad = identificacionDiscapacidad;
/* 235:    */   }
/* 236:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.vista.VInformacionEmpleado
 * JD-Core Version:    0.7.0.1
 */