/*   1:    */ package com.asinfo.as2.entities.sri;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Empresa;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import java.util.Date;
/*   6:    */ import javax.persistence.Column;
/*   7:    */ import javax.persistence.Entity;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.persistence.Temporal;
/*  16:    */ import javax.persistence.TemporalType;
/*  17:    */ import javax.persistence.Transient;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ import javax.validation.constraints.Size;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="autorizacion_empresa_SRI", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_empresa", "establecimiento", "punto_emision", "autorizacion"})})
/*  23:    */ public class AutorizacionProveedorSRI
/*  24:    */   extends EntidadBase
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="autorizacion_empresa_SRI", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="autorizacion_empresa_SRI")
/*  30:    */   @Column(name="id_autorizacion_empresa_SRI")
/*  31:    */   private int idAutorizacionEmpresaSRI;
/*  32:    */   @ManyToOne
/*  33:    */   @JoinColumn(name="id_empresa", nullable=true)
/*  34:    */   private Empresa empresa;
/*  35:    */   @Column(name="id_organizacion", nullable=false)
/*  36:    */   private int idOrganizacion;
/*  37:    */   @Column(name="id_sucursal", nullable=false)
/*  38:    */   private int idSucursal;
/*  39:    */   @Column(name="establecimiento", length=3, nullable=false)
/*  40:    */   @NotNull
/*  41:    */   @Size(min=3, max=3)
/*  42:    */   private String establecimiento;
/*  43:    */   @Column(name="punto_emision", length=3, nullable=false)
/*  44:    */   @NotNull
/*  45:    */   @Size(min=3, max=3)
/*  46:    */   private String puntoEmision;
/*  47:    */   @Column(name="autorizacion", length=37, nullable=false)
/*  48:    */   @NotNull
/*  49:    */   @Size(min=10, max=37)
/*  50:    */   private String autorizacion;
/*  51:    */   @Column(name="activo", nullable=false)
/*  52:    */   private boolean activo;
/*  53:    */   @Column(name="predeterminado", nullable=false)
/*  54:    */   private boolean predeterminado;
/*  55:    */   @Temporal(TemporalType.DATE)
/*  56:    */   @Column(name="fecha_desde", nullable=false, length=23)
/*  57:    */   @NotNull
/*  58:    */   private Date fechaDesde;
/*  59:    */   @Temporal(TemporalType.DATE)
/*  60:    */   @Column(name="fecha_hasta", nullable=false, length=23)
/*  61:    */   @NotNull
/*  62:    */   private Date fechaHasta;
/*  63:    */   @Column(name="indicador_factura_electronica", nullable=false)
/*  64:    */   private boolean indicadorFacturaElectronica;
/*  65:    */   @Transient
/*  66:    */   private String patronAutorizacion;
/*  67:    */   
/*  68:    */   public int getId()
/*  69:    */   {
/*  70:103 */     return this.idAutorizacionEmpresaSRI;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getIdAutorizacionEmpresaSRI()
/*  74:    */   {
/*  75:112 */     return this.idAutorizacionEmpresaSRI;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdAutorizacionEmpresaSRI(int idAutorizacionEmpresaSRI)
/*  79:    */   {
/*  80:122 */     this.idAutorizacionEmpresaSRI = idAutorizacionEmpresaSRI;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Empresa getEmpresa()
/*  84:    */   {
/*  85:131 */     return this.empresa;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setEmpresa(Empresa empresa)
/*  89:    */   {
/*  90:141 */     this.empresa = empresa;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getIdOrganizacion()
/*  94:    */   {
/*  95:150 */     return this.idOrganizacion;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setIdOrganizacion(int idOrganizacion)
/*  99:    */   {
/* 100:160 */     this.idOrganizacion = idOrganizacion;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public int getIdSucursal()
/* 104:    */   {
/* 105:169 */     return this.idSucursal;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setIdSucursal(int idSucursal)
/* 109:    */   {
/* 110:179 */     this.idSucursal = idSucursal;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getEstablecimiento()
/* 114:    */   {
/* 115:188 */     return this.establecimiento;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setEstablecimiento(String establecimiento)
/* 119:    */   {
/* 120:198 */     this.establecimiento = establecimiento;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public String getPuntoEmision()
/* 124:    */   {
/* 125:207 */     return this.puntoEmision;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setPuntoEmision(String puntoEmision)
/* 129:    */   {
/* 130:217 */     this.puntoEmision = puntoEmision;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public String getAutorizacion()
/* 134:    */   {
/* 135:226 */     return this.autorizacion;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setAutorizacion(String autorizacion)
/* 139:    */   {
/* 140:236 */     this.autorizacion = autorizacion;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public boolean isActivo()
/* 144:    */   {
/* 145:245 */     return this.activo;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setActivo(boolean activo)
/* 149:    */   {
/* 150:255 */     this.activo = activo;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public boolean isPredeterminado()
/* 154:    */   {
/* 155:264 */     return this.predeterminado;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setPredeterminado(boolean predeterminado)
/* 159:    */   {
/* 160:274 */     this.predeterminado = predeterminado;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public Date getFechaDesde()
/* 164:    */   {
/* 165:283 */     return this.fechaDesde;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setFechaDesde(Date fechaDesde)
/* 169:    */   {
/* 170:293 */     this.fechaDesde = fechaDesde;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public Date getFechaHasta()
/* 174:    */   {
/* 175:302 */     return this.fechaHasta;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setFechaHasta(Date fechaHasta)
/* 179:    */   {
/* 180:312 */     this.fechaHasta = fechaHasta;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public String getAutorizacionCompleto()
/* 184:    */   {
/* 185:316 */     return this.establecimiento + "-" + this.puntoEmision + " " + this.autorizacion;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public String toString()
/* 189:    */   {
/* 190:321 */     return this.autorizacion;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public boolean isIndicadorFacturaElectronica()
/* 194:    */   {
/* 195:330 */     return this.indicadorFacturaElectronica;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public void setIndicadorFacturaElectronica(boolean indicadorFacturaElectronica)
/* 199:    */   {
/* 200:340 */     this.indicadorFacturaElectronica = indicadorFacturaElectronica;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public String getPatronAutorizacion()
/* 204:    */   {
/* 205:349 */     this.patronAutorizacion = "";
/* 206:350 */     if (this.indicadorFacturaElectronica) {
/* 207:351 */       for (int i = 0; i < 37; i++) {
/* 208:352 */         this.patronAutorizacion += "9";
/* 209:    */       }
/* 210:    */     } else {
/* 211:355 */       for (int i = 0; i < 10; i++) {
/* 212:356 */         this.patronAutorizacion += "9";
/* 213:    */       }
/* 214:    */     }
/* 215:359 */     return this.patronAutorizacion;
/* 216:    */   }
/* 217:    */   
/* 218:    */   public void setPatronAutorizacion(String patronAutorizacion)
/* 219:    */   {
/* 220:369 */     this.patronAutorizacion = patronAutorizacion;
/* 221:    */   }
/* 222:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.sri.AutorizacionProveedorSRI
 * JD-Core Version:    0.7.0.1
 */