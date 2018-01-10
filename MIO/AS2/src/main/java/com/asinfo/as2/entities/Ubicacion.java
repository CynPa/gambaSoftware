/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.Table;
/*  11:    */ import javax.persistence.TableGenerator;
/*  12:    */ import javax.validation.constraints.Size;
/*  13:    */ 
/*  14:    */ @Entity
/*  15:    */ @Table(name="ubicacion")
/*  16:    */ public class Ubicacion
/*  17:    */   extends EntidadBase
/*  18:    */   implements Serializable
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 4940608110592953095L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="ubicacion", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="ubicacion")
/*  24:    */   @Column(name="id_ubicacion")
/*  25:    */   private int idUbicacion;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @Column(name="id_sucursal", nullable=false)
/*  29:    */   private int idSucursal;
/*  30:    */   @Column(name="direccion1", nullable=false, length=50)
/*  31:    */   @Size(max=50)
/*  32: 50 */   private String direccion1 = "";
/*  33:    */   @Column(name="direccion2", nullable=false, length=50)
/*  34:    */   @Size(max=50)
/*  35: 54 */   private String direccion2 = "";
/*  36:    */   @Column(name="direccion3", nullable=true, length=50)
/*  37:    */   @Size(max=50)
/*  38: 58 */   private String direccion3 = "";
/*  39:    */   @Column(name="direccion4", nullable=true, length=50)
/*  40:    */   @Size(max=50)
/*  41: 62 */   private String direccion4 = "";
/*  42:    */   @Column(name="direccion5", nullable=true, length=200)
/*  43:    */   @Size(max=200)
/*  44: 66 */   private String direccion5 = "";
/*  45:    */   @Column(name="latitud", nullable=true, precision=12, scale=8)
/*  46:    */   private BigDecimal latitud;
/*  47:    */   @Column(name="longitud", nullable=true, precision=12, scale=8)
/*  48:    */   private BigDecimal longitud;
/*  49:    */   
/*  50:    */   public int getId()
/*  51:    */   {
/*  52: 78 */     return this.idUbicacion;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int getIdUbicacion()
/*  56:    */   {
/*  57: 87 */     return this.idUbicacion;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void setIdUbicacion(int idUbicacion)
/*  61:    */   {
/*  62: 97 */     this.idUbicacion = idUbicacion;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getIdOrganizacion()
/*  66:    */   {
/*  67:106 */     return this.idOrganizacion;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public void setIdOrganizacion(int idOrganizacion)
/*  71:    */   {
/*  72:116 */     this.idOrganizacion = idOrganizacion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getIdSucursal()
/*  76:    */   {
/*  77:125 */     return this.idSucursal;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIdSucursal(int idSucursal)
/*  81:    */   {
/*  82:135 */     this.idSucursal = idSucursal;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public String getDireccion1()
/*  86:    */   {
/*  87:144 */     if (this.direccion1 == null) {
/*  88:145 */       this.direccion1 = "";
/*  89:    */     }
/*  90:147 */     return this.direccion1;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setDireccion1(String direccion1)
/*  94:    */   {
/*  95:157 */     this.direccion1 = direccion1;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getDireccion2()
/*  99:    */   {
/* 100:166 */     if (this.direccion2 == null) {
/* 101:167 */       this.direccion2 = "";
/* 102:    */     }
/* 103:169 */     return this.direccion2;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setDireccion2(String direccion2)
/* 107:    */   {
/* 108:179 */     this.direccion2 = direccion2;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getDireccion3()
/* 112:    */   {
/* 113:188 */     if (this.direccion3 == null) {
/* 114:189 */       this.direccion3 = "";
/* 115:    */     }
/* 116:191 */     return this.direccion3;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setDireccion3(String direccion3)
/* 120:    */   {
/* 121:201 */     this.direccion3 = direccion3;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String getDireccion4()
/* 125:    */   {
/* 126:210 */     if (this.direccion4 == null) {
/* 127:211 */       this.direccion4 = "";
/* 128:    */     }
/* 129:213 */     return this.direccion4;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setDireccion4(String direccion4)
/* 133:    */   {
/* 134:223 */     this.direccion4 = direccion4;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public BigDecimal getLatitud()
/* 138:    */   {
/* 139:232 */     return this.latitud;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setLatitud(BigDecimal latitud)
/* 143:    */   {
/* 144:242 */     this.latitud = latitud;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public BigDecimal getLongitud()
/* 148:    */   {
/* 149:251 */     return this.longitud;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setLongitud(BigDecimal longitud)
/* 153:    */   {
/* 154:261 */     this.longitud = longitud;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public String getDireccionCompleta()
/* 158:    */   {
/* 159:270 */     return getDireccion1().concat(" ").concat(getDireccion2()).concat(" ").concat(getDireccion3()).concat(" ").concat(getDireccion4());
/* 160:    */   }
/* 161:    */   
/* 162:    */   public String getDireccion5()
/* 163:    */   {
/* 164:279 */     return this.direccion5;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setDireccion5(String direccion5)
/* 168:    */   {
/* 169:289 */     this.direccion5 = direccion5;
/* 170:    */   }
/* 171:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.Ubicacion
 * JD-Core Version:    0.7.0.1
 */