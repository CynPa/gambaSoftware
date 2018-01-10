/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.FetchType;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.ManyToOne;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.Min;
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ import javax.validation.constraints.Size;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="configuracion_conciliacion_bancaria")
/*  20:    */ public class ConfiguracionConciliacionBancaria
/*  21:    */   extends EntidadBase
/*  22:    */   implements Serializable
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="configuracion_conciliacion_bancaria", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="configuracion_conciliacion_bancaria")
/*  28:    */   @Column(name="id_configuracion_conciliacion_bancaria")
/*  29:    */   private int idConfiguracionConciliacionBancaria;
/*  30:    */   @Column(name="id_organizacion", nullable=false)
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal", nullable=false)
/*  33:    */   private int idSucursal;
/*  34:    */   @NotNull
/*  35:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  36:    */   @JoinColumn(name="id_cuenta_bancaria_organizacion", nullable=false)
/*  37:    */   private CuentaBancariaOrganizacion cuentaBancariaOrganizacion;
/*  38:    */   @Min(1L)
/*  39:    */   @Column(name="columna_operacion")
/*  40:    */   private Integer columnaOperacion;
/*  41:    */   @Size(max=30)
/*  42:    */   @Column(name="nemonico_debito")
/*  43:    */   private String nemonicoDebito;
/*  44:    */   @Size(max=30)
/*  45:    */   @Column(name="nemonico_credito")
/*  46:    */   private String nemonicoCredito;
/*  47:    */   @Min(1L)
/*  48:    */   @Column(name="columna_monto")
/*  49:    */   private Integer columnaMonto;
/*  50:    */   @Min(1L)
/*  51:    */   @Column(name="columna_fecha")
/*  52:    */   private Integer columnaFecha;
/*  53:    */   @Size(max=20)
/*  54:    */   @Column(name="formato_fecha", nullable=true)
/*  55:    */   private String formatoFecha;
/*  56:    */   @Min(1L)
/*  57:    */   @Column(name="columna_documento")
/*  58:    */   private Integer columnaDocumento;
/*  59:    */   @Min(1L)
/*  60:    */   @Column(name="fila_inicial")
/*  61:    */   private Integer filaInicial;
/*  62:    */   @Column(name="descripcion", length=200)
/*  63:    */   @Size(max=200)
/*  64:    */   private String descripcion;
/*  65:    */   @Column(name="activo", nullable=false)
/*  66:    */   private boolean activo;
/*  67:    */   @Column(name="predeterminado", nullable=false)
/*  68:    */   private boolean predeterminado;
/*  69:    */   
/*  70:    */   public int getId()
/*  71:    */   {
/*  72: 96 */     return this.idConfiguracionConciliacionBancaria;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getIdConfiguracionConciliacionBancaria()
/*  76:    */   {
/*  77:103 */     return this.idConfiguracionConciliacionBancaria;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setIdConfiguracionConciliacionBancaria(int idConfiguracionConciliacionBancaria)
/*  81:    */   {
/*  82:111 */     this.idConfiguracionConciliacionBancaria = idConfiguracionConciliacionBancaria;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int getIdOrganizacion()
/*  86:    */   {
/*  87:118 */     return this.idOrganizacion;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public void setIdOrganizacion(int idOrganizacion)
/*  91:    */   {
/*  92:126 */     this.idOrganizacion = idOrganizacion;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getIdSucursal()
/*  96:    */   {
/*  97:133 */     return this.idSucursal;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setIdSucursal(int idSucursal)
/* 101:    */   {
/* 102:141 */     this.idSucursal = idSucursal;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public CuentaBancariaOrganizacion getCuentaBancariaOrganizacion()
/* 106:    */   {
/* 107:148 */     return this.cuentaBancariaOrganizacion;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setCuentaBancariaOrganizacion(CuentaBancariaOrganizacion cuentaBancariaOrganizacion)
/* 111:    */   {
/* 112:156 */     this.cuentaBancariaOrganizacion = cuentaBancariaOrganizacion;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Integer getColumnaOperacion()
/* 116:    */   {
/* 117:163 */     return this.columnaOperacion;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void setColumnaOperacion(Integer columnaOperacion)
/* 121:    */   {
/* 122:171 */     this.columnaOperacion = columnaOperacion;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public String getNemonicoDebito()
/* 126:    */   {
/* 127:178 */     return this.nemonicoDebito;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public void setNemonicoDebito(String nemonicoDebito)
/* 131:    */   {
/* 132:186 */     this.nemonicoDebito = nemonicoDebito;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String getNemonicoCredito()
/* 136:    */   {
/* 137:193 */     return this.nemonicoCredito;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public void setNemonicoCredito(String nemonicoCredito)
/* 141:    */   {
/* 142:201 */     this.nemonicoCredito = nemonicoCredito;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public Integer getColumnaMonto()
/* 146:    */   {
/* 147:208 */     return this.columnaMonto;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public void setColumnaMonto(Integer columnaMonto)
/* 151:    */   {
/* 152:216 */     this.columnaMonto = columnaMonto;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Integer getColumnaFecha()
/* 156:    */   {
/* 157:223 */     return this.columnaFecha;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setColumnaFecha(Integer columnaFecha)
/* 161:    */   {
/* 162:231 */     this.columnaFecha = columnaFecha;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public String getFormatoFecha()
/* 166:    */   {
/* 167:238 */     return this.formatoFecha;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setFormatoFecha(String formatoFecha)
/* 171:    */   {
/* 172:246 */     this.formatoFecha = formatoFecha;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public Integer getColumnaDocumento()
/* 176:    */   {
/* 177:253 */     return this.columnaDocumento;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setColumnaDocumento(Integer columnaDocumento)
/* 181:    */   {
/* 182:261 */     this.columnaDocumento = columnaDocumento;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public String getDescripcion()
/* 186:    */   {
/* 187:268 */     return this.descripcion;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setDescripcion(String descripcion)
/* 191:    */   {
/* 192:276 */     this.descripcion = descripcion;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public boolean isActivo()
/* 196:    */   {
/* 197:283 */     return this.activo;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setActivo(boolean activo)
/* 201:    */   {
/* 202:291 */     this.activo = activo;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public boolean isPredeterminado()
/* 206:    */   {
/* 207:298 */     return this.predeterminado;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setPredeterminado(boolean predeterminado)
/* 211:    */   {
/* 212:306 */     this.predeterminado = predeterminado;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public Integer getFilaInicial()
/* 216:    */   {
/* 217:313 */     return this.filaInicial;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setFilaInicial(Integer filaInicial)
/* 221:    */   {
/* 222:321 */     this.filaInicial = filaInicial;
/* 223:    */   }
/* 224:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.ConfiguracionConciliacionBancaria
 * JD-Core Version:    0.7.0.1
 */