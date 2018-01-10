/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.enumeraciones.Estado;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.util.Date;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.Enumerated;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ 
/*  13:    */ @Entity
/*  14:    */ @Table(name="tmp_reporte_anticipo_proveedor")
/*  15:    */ public class ReporteAnticipoProveedor
/*  16:    */   implements Serializable
/*  17:    */ {
/*  18:    */   private static final long serialVersionUID = -8430909592950728015L;
/*  19:    */   @Id
/*  20:    */   @Column(name="id_reporte_anticipo_proveedor")
/*  21:    */   private int idReporteAnticipoProveedor;
/*  22:    */   @Column(name="id_empresa")
/*  23:    */   private int idEmpresa;
/*  24:    */   @Column(name="nombre_comercial")
/*  25:    */   private String nombreComercial;
/*  26:    */   @Column(name="nombre_fiscal")
/*  27:    */   private String nombreFiscal;
/*  28:    */   @Column(name="identificacion")
/*  29:    */   private String identificacion;
/*  30:    */   @Column(name="fecha")
/*  31:    */   private Date fecha;
/*  32:    */   @Column(name="valor")
/*  33: 60 */   private BigDecimal valor = BigDecimal.ZERO;
/*  34:    */   @Column(name="numero_anticipo")
/*  35:    */   private String numeroAnticipo;
/*  36:    */   @Column(name="valor_anticipo")
/*  37: 66 */   private BigDecimal valorAnticipo = BigDecimal.ZERO;
/*  38:    */   @Column(name="numero_liquidacion")
/*  39:    */   private String numeroLiquidacion;
/*  40:    */   @Column(name="valor_liquidacion_anticipo")
/*  41: 72 */   private BigDecimal valorLiquidacionAnticipo = BigDecimal.ZERO;
/*  42:    */   @Column(name="tipo")
/*  43:    */   private String tipo;
/*  44:    */   @Column(name="numero_factura")
/*  45:    */   private String numeroFactura;
/*  46:    */   @Enumerated
/*  47:    */   @Column
/*  48:    */   private Estado estadoAnticipo;
/*  49:    */   
/*  50:    */   public ReporteAnticipoProveedor() {}
/*  51:    */   
/*  52:    */   public ReporteAnticipoProveedor(int idEmpresa, String nombreComercial, String nombreFiscal, String identificacion, BigDecimal valor, String tipo, Estado estadoAnticipo)
/*  53:    */   {
/*  54:104 */     this(idEmpresa, nombreComercial, nombreFiscal, identificacion, "", valor, tipo, estadoAnticipo);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public ReporteAnticipoProveedor(int idEmpresa, String nombreComercial, String nombreFiscal, String identificacion, String numeroAnticipo, BigDecimal valor, String tipo, Estado estadoAnticipo)
/*  58:    */   {
/*  59:120 */     this.idEmpresa = idEmpresa;
/*  60:121 */     this.nombreComercial = nombreComercial;
/*  61:122 */     this.nombreFiscal = nombreFiscal;
/*  62:123 */     this.identificacion = identificacion;
/*  63:124 */     this.numeroAnticipo = numeroAnticipo;
/*  64:125 */     this.valor = valor;
/*  65:126 */     this.tipo = tipo;
/*  66:127 */     this.estadoAnticipo = estadoAnticipo;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public ReporteAnticipoProveedor(int idEmpresa, String nombreComercial, String nombreFiscal, String identificacion, Date fecha, BigDecimal valor, String numeroAnticipo, BigDecimal valorAnticipo, String numeroLiquidacion, BigDecimal valorLiquidacionAnticipo, String tipo, Estado estadoAnticipo)
/*  70:    */   {
/*  71:144 */     this.idEmpresa = idEmpresa;
/*  72:145 */     this.nombreComercial = nombreComercial;
/*  73:146 */     this.nombreFiscal = nombreFiscal;
/*  74:147 */     this.identificacion = identificacion;
/*  75:148 */     this.fecha = fecha;
/*  76:149 */     this.valor = valor;
/*  77:150 */     this.numeroAnticipo = numeroAnticipo;
/*  78:151 */     this.valorAnticipo = valorAnticipo;
/*  79:152 */     this.numeroLiquidacion = numeroLiquidacion;
/*  80:153 */     this.valorLiquidacionAnticipo = valorLiquidacionAnticipo;
/*  81:154 */     this.tipo = tipo;
/*  82:155 */     this.estadoAnticipo = estadoAnticipo;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public ReporteAnticipoProveedor(int idEmpresa, String nombreComercial, String nombreFiscal, String identificacion, Date fecha, BigDecimal valor, String numeroAnticipo, BigDecimal valorAnticipo, String numeroLiquidacion, BigDecimal valorLiquidacionAnticipo, String tipo, String numeroFactura)
/*  86:    */   {
/*  87:177 */     this(idEmpresa, nombreComercial, nombreFiscal, identificacion, fecha, valor, numeroAnticipo, valorAnticipo, numeroLiquidacion, valorLiquidacionAnticipo, tipo, Estado.SIN_ESTADO);
/*  88:    */     
/*  89:179 */     this.numeroFactura = numeroFactura;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int getIdReporteAnticipoProveedor()
/*  93:    */   {
/*  94:188 */     return this.idReporteAnticipoProveedor;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setIdReporteAnticipoProveedor(int idReporteAnticipoProveedor)
/*  98:    */   {
/*  99:198 */     this.idReporteAnticipoProveedor = idReporteAnticipoProveedor;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String getNombreComercial()
/* 103:    */   {
/* 104:207 */     return this.nombreComercial;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setNombreComercial(String nombreComercial)
/* 108:    */   {
/* 109:217 */     this.nombreComercial = nombreComercial;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String getNombreFiscal()
/* 113:    */   {
/* 114:226 */     return this.nombreFiscal;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setNombreFiscal(String nombreFiscal)
/* 118:    */   {
/* 119:236 */     this.nombreFiscal = nombreFiscal;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public String getIdentificacion()
/* 123:    */   {
/* 124:245 */     return this.identificacion;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setIdentificacion(String identificacion)
/* 128:    */   {
/* 129:255 */     this.identificacion = identificacion;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public Date getFecha()
/* 133:    */   {
/* 134:264 */     return this.fecha;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setFecha(Date fecha)
/* 138:    */   {
/* 139:274 */     this.fecha = fecha;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String getNumeroAnticipo()
/* 143:    */   {
/* 144:283 */     return this.numeroAnticipo;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setNumeroAnticipo(String numeroAnticipo)
/* 148:    */   {
/* 149:293 */     this.numeroAnticipo = numeroAnticipo;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public String getNumeroLiquidacion()
/* 153:    */   {
/* 154:302 */     return this.numeroLiquidacion;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setNumeroLiquidacion(String numeroLiquidacion)
/* 158:    */   {
/* 159:312 */     this.numeroLiquidacion = numeroLiquidacion;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public BigDecimal getValor()
/* 163:    */   {
/* 164:321 */     return this.valor;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setValor(BigDecimal valor)
/* 168:    */   {
/* 169:331 */     this.valor = valor;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public BigDecimal getValorAnticipo()
/* 173:    */   {
/* 174:340 */     return this.valorAnticipo;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setValorAnticipo(BigDecimal valorAnticipo)
/* 178:    */   {
/* 179:350 */     this.valorAnticipo = valorAnticipo;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public BigDecimal getValorLiquidacionAnticipo()
/* 183:    */   {
/* 184:359 */     return this.valorLiquidacionAnticipo;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setValorLiquidacionAnticipo(BigDecimal valorLiquidacionAnticipo)
/* 188:    */   {
/* 189:369 */     this.valorLiquidacionAnticipo = valorLiquidacionAnticipo;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public String getTipo()
/* 193:    */   {
/* 194:378 */     return this.tipo;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void setTipo(String tipo)
/* 198:    */   {
/* 199:388 */     this.tipo = tipo;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public int getIdEmpresa()
/* 203:    */   {
/* 204:397 */     return this.idEmpresa;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void setIdEmpresa(int idEmpresa)
/* 208:    */   {
/* 209:407 */     this.idEmpresa = idEmpresa;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public String getNumeroFactura()
/* 213:    */   {
/* 214:416 */     return this.numeroFactura;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setNumeroFactura(String numeroFactura)
/* 218:    */   {
/* 219:426 */     this.numeroFactura = numeroFactura;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public Estado getEstadoAnticipo()
/* 223:    */   {
/* 224:435 */     return this.estadoAnticipo;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setEstadoAnticipo(Estado estadoAnticipo)
/* 228:    */   {
/* 229:445 */     this.estadoAnticipo = estadoAnticipo;
/* 230:    */   }
/* 231:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.ReporteAnticipoProveedor
 * JD-Core Version:    0.7.0.1
 */