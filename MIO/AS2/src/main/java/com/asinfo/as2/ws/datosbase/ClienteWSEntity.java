/*   1:    */ package com.asinfo.as2.ws.datosbase;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Ciudad;
/*   4:    */ import com.asinfo.as2.entities.Cliente;
/*   5:    */ import com.asinfo.as2.entities.CondicionPago;
/*   6:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   7:    */ import com.asinfo.as2.entities.Empresa;
/*   8:    */ import com.asinfo.as2.entities.FormaPago;
/*   9:    */ import com.asinfo.as2.entities.Pais;
/*  10:    */ import com.asinfo.as2.entities.Provincia;
/*  11:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*  12:    */ import com.asinfo.as2.entities.Ubicacion;
/*  13:    */ import com.asinfo.as2.enumeraciones.TipoEmpresa;
/*  14:    */ import java.io.Serializable;
/*  15:    */ import java.util.List;
/*  16:    */ 
/*  17:    */ public class ClienteWSEntity
/*  18:    */   implements Serializable
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 1L;
/*  21:    */   private String tipoIdentificacion;
/*  22:    */   private String identificacionNueva;
/*  23:    */   private String identificacion;
/*  24:    */   private String nombre;
/*  25:    */   private Integer tipoCliente;
/*  26:    */   private String telefono;
/*  27:    */   private String email;
/*  28: 41 */   private String codigoISOPais = "ECU";
/*  29: 43 */   private String pais = "ECUADOR";
/*  30: 45 */   private String provincia = "Pichincha";
/*  31: 47 */   private String ciudad = "Quito";
/*  32:    */   private String direccion;
/*  33:    */   private String formaPago;
/*  34: 53 */   private Integer condicionPago = Integer.valueOf(1);
/*  35:    */   private String descripcion;
/*  36:    */   
/*  37:    */   public ClienteWSEntity() {}
/*  38:    */   
/*  39:    */   public ClienteWSEntity(Empresa empresa)
/*  40:    */   {
/*  41: 62 */     this.tipoIdentificacion = empresa.getTipoIdentificacion().getCodigo();
/*  42:    */     
/*  43: 64 */     this.identificacion = empresa.getIdentificacion();
/*  44:    */     
/*  45: 66 */     this.nombre = empresa.getNombreFiscal();
/*  46:    */     
/*  47: 68 */     this.tipoCliente = Integer.valueOf(empresa.getTipoEmpresa().equals(TipoEmpresa.NATURAL) ? 1 : 2);
/*  48:    */     
/*  49: 70 */     this.telefono = ((DireccionEmpresa)empresa.getDirecciones().get(0)).getTelefono1();
/*  50:    */     
/*  51: 72 */     this.email = empresa.getEmail1();
/*  52:    */     
/*  53: 74 */     this.codigoISOPais = ((DireccionEmpresa)empresa.getDirecciones().get(0)).getCiudad().getProvincia().getPais().getCodigoIso();
/*  54:    */     
/*  55: 76 */     this.pais = ((DireccionEmpresa)empresa.getDirecciones().get(0)).getCiudad().getProvincia().getPais().getNombre();
/*  56:    */     
/*  57: 78 */     this.provincia = ((DireccionEmpresa)empresa.getDirecciones().get(0)).getCiudad().getProvincia().getNombre();
/*  58:    */     
/*  59: 80 */     this.ciudad = ((DireccionEmpresa)empresa.getDirecciones().get(0)).getCiudad().getNombre();
/*  60:    */     
/*  61: 82 */     this.direccion = (((DireccionEmpresa)empresa.getDirecciones().get(0)).getUbicacion().getDireccion1() + ((DireccionEmpresa)empresa.getDirecciones().get(0)).getUbicacion().getDireccion2() + ((DireccionEmpresa)empresa.getDirecciones().get(0)).getUbicacion().getDireccion3() + ((DireccionEmpresa)empresa.getDirecciones().get(0)).getUbicacion().getDireccion4());
/*  62:    */     
/*  63: 84 */     this.formaPago = empresa.getCliente().getFormaPago().getCodigo();
/*  64:    */     
/*  65: 86 */     this.condicionPago = Integer.valueOf(empresa.getCliente().getCondicionPago().getDiasPlazo());
/*  66:    */     
/*  67: 88 */     this.descripcion = empresa.getDescripcion();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String getTipoIdentificacion()
/*  71:    */   {
/*  72: 92 */     return this.tipoIdentificacion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setTipoIdentificacion(String tipoIdentificacion)
/*  76:    */   {
/*  77: 96 */     this.tipoIdentificacion = tipoIdentificacion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public String getIdentificacion()
/*  81:    */   {
/*  82:100 */     return this.identificacion;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdentificacion(String identificacion)
/*  86:    */   {
/*  87:104 */     this.identificacion = identificacion;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String getNombre()
/*  91:    */   {
/*  92:108 */     return this.nombre;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setNombre(String nombre)
/*  96:    */   {
/*  97:112 */     this.nombre = nombre;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public Integer getTipoCliente()
/* 101:    */   {
/* 102:116 */     return this.tipoCliente;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setTipoCliente(Integer tipoCliente)
/* 106:    */   {
/* 107:120 */     this.tipoCliente = tipoCliente;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getTelefono()
/* 111:    */   {
/* 112:124 */     return this.telefono;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setTelefono(String telefono)
/* 116:    */   {
/* 117:128 */     this.telefono = telefono;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String getEmail()
/* 121:    */   {
/* 122:132 */     return this.email;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setEmail(String email)
/* 126:    */   {
/* 127:136 */     this.email = email;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public String getDireccion()
/* 131:    */   {
/* 132:140 */     return this.direccion;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setDireccion(String direccion)
/* 136:    */   {
/* 137:144 */     this.direccion = direccion;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String getFormaPago()
/* 141:    */   {
/* 142:148 */     return this.formaPago;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setFormaPago(String formaPago)
/* 146:    */   {
/* 147:152 */     this.formaPago = formaPago;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public Integer getCondicionPago()
/* 151:    */   {
/* 152:156 */     return this.condicionPago;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setCondicionPago(Integer condicionPago)
/* 156:    */   {
/* 157:160 */     this.condicionPago = condicionPago;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public String getDescripcion()
/* 161:    */   {
/* 162:164 */     return this.descripcion;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setDescripcion(String descripcion)
/* 166:    */   {
/* 167:168 */     this.descripcion = descripcion;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String getCodigoISOPais()
/* 171:    */   {
/* 172:172 */     return this.codigoISOPais;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setCodigoISOPais(String codigoISOPais)
/* 176:    */   {
/* 177:176 */     this.codigoISOPais = codigoISOPais;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String getPais()
/* 181:    */   {
/* 182:180 */     return this.pais;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setPais(String pais)
/* 186:    */   {
/* 187:184 */     this.pais = pais;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public String getProvincia()
/* 191:    */   {
/* 192:188 */     return this.provincia;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setProvincia(String provincia)
/* 196:    */   {
/* 197:192 */     this.provincia = provincia;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public String getCiudad()
/* 201:    */   {
/* 202:196 */     return this.ciudad;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setCiudad(String ciudad)
/* 206:    */   {
/* 207:200 */     this.ciudad = ciudad;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public String getIdentificacionNueva()
/* 211:    */   {
/* 212:204 */     return this.identificacionNueva;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setIdentificacionNueva(String identificacionNueva)
/* 216:    */   {
/* 217:208 */     this.identificacionNueva = identificacionNueva;
/* 218:    */   }
/* 219:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.ws.datosbase.ClienteWSEntity
 * JD-Core Version:    0.7.0.1
 */