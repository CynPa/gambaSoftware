/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import javax.persistence.Column;
/*   5:    */ import javax.persistence.Entity;
/*   6:    */ import javax.persistence.GeneratedValue;
/*   7:    */ import javax.persistence.GenerationType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.Table;
/*  10:    */ import javax.persistence.TableGenerator;
/*  11:    */ import javax.validation.constraints.Max;
/*  12:    */ import javax.validation.constraints.Min;
/*  13:    */ import javax.validation.constraints.NotNull;
/*  14:    */ import javax.validation.constraints.Size;
/*  15:    */ import org.hibernate.annotations.ColumnDefault;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="condicion_pago", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  19:    */ public class CondicionPago
/*  20:    */   extends EntidadBase
/*  21:    */   implements Serializable
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="condicion_pago", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="condicion_pago")
/*  27:    */   @Column(name="id_condicion_pago", unique=true, nullable=false)
/*  28:    */   private int idCondicionPago;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   private int idSucursal;
/*  33:    */   @Column(name="nombre", nullable=false, length=50)
/*  34:    */   @NotNull
/*  35:    */   @Size(min=2, max=50)
/*  36:    */   private String nombre;
/*  37:    */   @Column(name="codigo", length=10, nullable=false)
/*  38:    */   @Size(min=2, max=10)
/*  39:    */   @NotNull
/*  40:    */   private String codigo;
/*  41:    */   @Column(name="dias_plazo", nullable=false)
/*  42:    */   @Min(0L)
/*  43:    */   private int diasPlazo;
/*  44:    */   @Column(name="meses_plazo", nullable=false)
/*  45:    */   @Min(0L)
/*  46:    */   private int mesesPlazo;
/*  47:    */   @Column(name="indicador_fecha_fija", nullable=false)
/*  48:    */   private boolean indicadorFechaFija;
/*  49:    */   @Column(name="dia_vencimiento", nullable=false)
/*  50:    */   @Min(0L)
/*  51:    */   @Max(31L)
/*  52:    */   private int diaVencimiento;
/*  53:    */   @Column(name="descripcion", length=200, nullable=true)
/*  54:    */   @Size(max=200)
/*  55:    */   private String descripcion;
/*  56:    */   @Column(name="activo", nullable=false)
/*  57:    */   private boolean activo;
/*  58:    */   @Column(name="predeterminado", nullable=false)
/*  59:    */   private boolean predeterminado;
/*  60:    */   @Min(0L)
/*  61:    */   @Column(name="peso")
/*  62:    */   @ColumnDefault("0")
/*  63:    */   @NotNull
/*  64: 77 */   private int peso = 0;
/*  65:    */   
/*  66:    */   public CondicionPago() {}
/*  67:    */   
/*  68:    */   public CondicionPago(int idCondicionPago, String nombre, String codigo)
/*  69:    */   {
/*  70: 92 */     this.idCondicionPago = idCondicionPago;
/*  71: 93 */     this.codigo = codigo;
/*  72: 94 */     this.nombre = nombre;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getId()
/*  76:    */   {
/*  77: 99 */     return this.idCondicionPago;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdCondicionPago()
/*  81:    */   {
/*  82:108 */     return this.idCondicionPago;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdCondicionPago(int idCondicionPago)
/*  86:    */   {
/*  87:118 */     this.idCondicionPago = idCondicionPago;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getIdOrganizacion()
/*  91:    */   {
/*  92:127 */     return this.idOrganizacion;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setIdOrganizacion(int idOrganizacion)
/*  96:    */   {
/*  97:137 */     this.idOrganizacion = idOrganizacion;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int getIdSucursal()
/* 101:    */   {
/* 102:146 */     return this.idSucursal;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setIdSucursal(int idSucursal)
/* 106:    */   {
/* 107:156 */     this.idSucursal = idSucursal;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getNombre()
/* 111:    */   {
/* 112:165 */     return this.nombre;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setNombre(String nombre)
/* 116:    */   {
/* 117:175 */     this.nombre = nombre;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int getDiasPlazo()
/* 121:    */   {
/* 122:184 */     return this.diasPlazo;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setDiasPlazo(int diasPlazo)
/* 126:    */   {
/* 127:194 */     this.diasPlazo = diasPlazo;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public int getMesesPlazo()
/* 131:    */   {
/* 132:203 */     return this.mesesPlazo;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setMesesPlazo(int mesesPlazo)
/* 136:    */   {
/* 137:213 */     this.mesesPlazo = mesesPlazo;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String getDescripcion()
/* 141:    */   {
/* 142:222 */     return this.descripcion;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setDescripcion(String descripcion)
/* 146:    */   {
/* 147:232 */     this.descripcion = descripcion;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public boolean isActivo()
/* 151:    */   {
/* 152:241 */     return this.activo;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setActivo(boolean activo)
/* 156:    */   {
/* 157:251 */     this.activo = activo;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public boolean isPredeterminado()
/* 161:    */   {
/* 162:260 */     return this.predeterminado;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setPredeterminado(boolean predeterminado)
/* 166:    */   {
/* 167:270 */     this.predeterminado = predeterminado;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String getCodigo()
/* 171:    */   {
/* 172:279 */     return this.codigo;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setCodigo(String codigo)
/* 176:    */   {
/* 177:289 */     this.codigo = codigo;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String toString()
/* 181:    */   {
/* 182:294 */     return this.nombre;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public boolean isIndicadorFechaFija()
/* 186:    */   {
/* 187:298 */     return this.indicadorFechaFija;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setIndicadorFechaFija(boolean indicadorFechaFija)
/* 191:    */   {
/* 192:302 */     this.indicadorFechaFija = indicadorFechaFija;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public int getDiaVencimiento()
/* 196:    */   {
/* 197:306 */     return this.diaVencimiento;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setDiaVencimiento(int diaVencimiento)
/* 201:    */   {
/* 202:310 */     this.diaVencimiento = diaVencimiento;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public int getPeso()
/* 206:    */   {
/* 207:314 */     return this.peso;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setPeso(int peso)
/* 211:    */   {
/* 212:318 */     this.peso = peso;
/* 213:    */   }
/* 214:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CondicionPago
 * JD-Core Version:    0.7.0.1
 */