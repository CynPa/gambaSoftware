/*   1:    */ package com.asinfo.as2.rs.datosbase.dto;
/*   2:    */ 
/*   3:    */ import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
/*   4:    */ import java.io.Serializable;
/*   5:    */ 
/*   6:    */ @JsonIgnoreProperties(ignoreUnknown=true)
/*   7:    */ public class ContactoResponseDto
/*   8:    */   extends EntidadBaseResponseDto
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private Integer idContacto;
/*  12:    */   private Integer idEmpresa;
/*  13:    */   private String nombreFiscalEmpresa;
/*  14:    */   private String nombreComercialEmpresa;
/*  15:    */   private String identificacionEmpresa;
/*  16:    */   private String cargo;
/*  17:    */   private String nombre;
/*  18:    */   private String telefono1;
/*  19:    */   private String extension1;
/*  20:    */   private String telefono2;
/*  21:    */   private String extension2;
/*  22:    */   private String email1;
/*  23:    */   private String email2;
/*  24: 23 */   private boolean activo = true;
/*  25:    */   private CatalogoGenericoResponseDto tipoContacto;
/*  26: 26 */   private int hashCode = 0;
/*  27:    */   
/*  28:    */   public int getHashCode()
/*  29:    */   {
/*  30: 29 */     this.hashCode = hashCode();
/*  31: 30 */     return this.hashCode;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public int hashCode()
/*  35:    */   {
/*  36: 35 */     int hash = super.hashCode();
/*  37: 36 */     hash += hash * 11 + (this.idContacto + "").hashCode();
/*  38: 37 */     hash += hash * 13 + (this.idEmpresa + "").hashCode();
/*  39: 38 */     hash += hash * 15 + (this.nombreFiscalEmpresa + "").hashCode();
/*  40:    */     
/*  41: 40 */     hash += hash * 15 + (this.nombreComercialEmpresa + "").hashCode();
/*  42: 41 */     hash += hash * 15 + (this.identificacionEmpresa + "").hashCode();
/*  43: 42 */     hash += hash * 15 + (this.cargo + "").hashCode();
/*  44: 43 */     hash += hash * 15 + (this.nombre + "").hashCode();
/*  45: 44 */     hash += hash * 15 + (this.telefono1 + "").hashCode();
/*  46: 45 */     hash += hash * 15 + (this.extension1 + "").hashCode();
/*  47: 46 */     hash += hash * 15 + (this.telefono2 + "").hashCode();
/*  48: 47 */     hash += hash * 15 + (this.extension2 + "").hashCode();
/*  49: 48 */     hash += hash * 15 + (this.email1 + "").hashCode();
/*  50: 49 */     hash += hash * 15 + (this.email2 + "").hashCode();
/*  51: 50 */     hash += hash * 15 + (this.activo + "").hashCode();
/*  52: 51 */     hash += hash * 15 + (this.tipoContacto != null ? this.tipoContacto.getHashCode() : 0);
/*  53: 52 */     return hash;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public Integer getIdContacto()
/*  57:    */   {
/*  58: 56 */     return this.idContacto;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void setIdContacto(Integer idContacto)
/*  62:    */   {
/*  63: 60 */     this.idContacto = idContacto;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public Integer getIdEmpresa()
/*  67:    */   {
/*  68: 64 */     return this.idEmpresa;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void setIdEmpresa(Integer idEmpresa)
/*  72:    */   {
/*  73: 68 */     this.idEmpresa = idEmpresa;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public String getNombreFiscalEmpresa()
/*  77:    */   {
/*  78: 72 */     return this.nombreFiscalEmpresa;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setNombreFiscalEmpresa(String nombreFiscalEmpresa)
/*  82:    */   {
/*  83: 76 */     this.nombreFiscalEmpresa = nombreFiscalEmpresa;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public String getNombreComercialEmpresa()
/*  87:    */   {
/*  88: 80 */     return this.nombreComercialEmpresa;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setNombreComercialEmpresa(String nombreComercialEmpresa)
/*  92:    */   {
/*  93: 84 */     this.nombreComercialEmpresa = nombreComercialEmpresa;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public String getIdentificacionEmpresa()
/*  97:    */   {
/*  98: 88 */     return this.identificacionEmpresa;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setIdentificacionEmpresa(String identificacionEmpresa)
/* 102:    */   {
/* 103: 92 */     this.identificacionEmpresa = identificacionEmpresa;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getCargo()
/* 107:    */   {
/* 108: 96 */     return this.cargo;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setCargo(String cargo)
/* 112:    */   {
/* 113:100 */     this.cargo = cargo;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getNombre()
/* 117:    */   {
/* 118:104 */     return this.nombre;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setNombre(String nombre)
/* 122:    */   {
/* 123:108 */     this.nombre = nombre;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String getTelefono1()
/* 127:    */   {
/* 128:112 */     return this.telefono1;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setTelefono1(String telefono1)
/* 132:    */   {
/* 133:116 */     this.telefono1 = telefono1;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public String getExtension1()
/* 137:    */   {
/* 138:120 */     return this.extension1;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setExtension1(String extension1)
/* 142:    */   {
/* 143:124 */     this.extension1 = extension1;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String getTelefono2()
/* 147:    */   {
/* 148:128 */     return this.telefono2;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setTelefono2(String telefono2)
/* 152:    */   {
/* 153:132 */     this.telefono2 = telefono2;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public String getExtension2()
/* 157:    */   {
/* 158:136 */     return this.extension2;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setExtension2(String extension2)
/* 162:    */   {
/* 163:140 */     this.extension2 = extension2;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public String getEmail1()
/* 167:    */   {
/* 168:144 */     return this.email1;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setEmail1(String email1)
/* 172:    */   {
/* 173:148 */     this.email1 = email1;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String getEmail2()
/* 177:    */   {
/* 178:152 */     return this.email2;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setEmail2(String email2)
/* 182:    */   {
/* 183:156 */     this.email2 = email2;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public boolean isActivo()
/* 187:    */   {
/* 188:160 */     return this.activo;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setActivo(boolean activo)
/* 192:    */   {
/* 193:164 */     this.activo = activo;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public CatalogoGenericoResponseDto getTipoContacto()
/* 197:    */   {
/* 198:168 */     return this.tipoContacto;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void setTipoContacto(CatalogoGenericoResponseDto tipoContacto)
/* 202:    */   {
/* 203:172 */     this.tipoContacto = tipoContacto;
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void setHashCode(int hashCode)
/* 207:    */   {
/* 208:176 */     this.hashCode = hashCode;
/* 209:    */   }
/* 210:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.rs.datosbase.dto.ContactoResponseDto
 * JD-Core Version:    0.7.0.1
 */