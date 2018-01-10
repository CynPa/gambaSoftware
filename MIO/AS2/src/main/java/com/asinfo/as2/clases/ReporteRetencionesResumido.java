/*   1:    */ package com.asinfo.as2.clases;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.util.Date;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.Id;
/*   9:    */ import javax.persistence.JoinColumn;
/*  10:    */ import javax.persistence.ManyToOne;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ 
/*  13:    */ @Entity
/*  14:    */ @Table(name="tmp_compras_ventas_retenciones")
/*  15:    */ public class ReporteRetencionesResumido
/*  16:    */ {
/*  17:    */   @Id
/*  18:    */   @Column(name="id_compras_ventas_retenciones_resumido")
/*  19:    */   private int idComprasVentasRetenciones;
/*  20:    */   @Column(name="fecha_emision_retencion")
/*  21:    */   private Date fechaEmisionRetencion;
/*  22:    */   @Column(name="fecha_registro")
/*  23:    */   private Date fechaRegistro;
/*  24:    */   @Column(name="fecha_emision")
/*  25:    */   private Date fechaEmision;
/*  26:    */   @Column(name="numero")
/*  27:    */   private String numero;
/*  28:    */   @Column(name="identificacion_proveedor")
/*  29:    */   private String identificacionProveedor;
/*  30:    */   @Column(name="descripcion")
/*  31:    */   private String descripcion;
/*  32:    */   @Column(name="valor1")
/*  33:    */   private BigDecimal valor1;
/*  34:    */   @Column(name="porcentaje_retencion")
/*  35:    */   private BigDecimal porcentajeRetencion;
/*  36:    */   @Column(name="valor_retencion")
/*  37:    */   private BigDecimal valorRetencion;
/*  38:    */   @Column(name="codigo")
/*  39:    */   private String codigo;
/*  40:    */   @Column(name="tipo_retencion")
/*  41:    */   private String tipoRetencion;
/*  42:    */   @Column(name="numero_retencion")
/*  43:    */   private String numeroRetencion;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_reporte_general_retencion", nullable=true)
/*  46:    */   private ReporteGeneralRetencion reporteGeneralRetencion;
/*  47:    */   
/*  48:    */   public ReporteRetencionesResumido(Date fechaEmisionRetencion, Date fechaRegistro, Date fechaEmision, String numero, String identificacionProveedor, String descripcion, Number valor1, BigDecimal porcentajeRetencion, BigDecimal valorRetencion, String codigo, String tipoRetencion, String numeroRetencion)
/*  49:    */   {
/*  50: 96 */     this.fechaEmisionRetencion = fechaEmisionRetencion;
/*  51: 97 */     this.fechaRegistro = fechaRegistro;
/*  52: 98 */     this.fechaEmision = fechaEmision;
/*  53: 99 */     this.numero = numero;
/*  54:100 */     this.identificacionProveedor = identificacionProveedor;
/*  55:101 */     this.descripcion = descripcion;
/*  56:102 */     this.valor1 = new BigDecimal(valor1.toString());
/*  57:103 */     this.porcentajeRetencion = porcentajeRetencion;
/*  58:104 */     this.valorRetencion = valorRetencion;
/*  59:105 */     this.codigo = codigo;
/*  60:106 */     this.tipoRetencion = tipoRetencion;
/*  61:107 */     this.numeroRetencion = numeroRetencion;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getIdComprasVentasRetenciones()
/*  65:    */   {
/*  66:116 */     return this.idComprasVentasRetenciones;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIdComprasVentasRetenciones(int idComprasVentasRetenciones)
/*  70:    */   {
/*  71:126 */     this.idComprasVentasRetenciones = idComprasVentasRetenciones;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Date getFechaEmisionRetencion()
/*  75:    */   {
/*  76:135 */     return this.fechaEmisionRetencion;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setFechaEmisionRetencion(Date fechaEmisionRetencion)
/*  80:    */   {
/*  81:145 */     this.fechaEmisionRetencion = fechaEmisionRetencion;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Date getFechaRegistro()
/*  85:    */   {
/*  86:154 */     return this.fechaRegistro;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setFechaRegistro(Date fechaRegistro)
/*  90:    */   {
/*  91:164 */     this.fechaRegistro = fechaRegistro;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public Date getFechaEmision()
/*  95:    */   {
/*  96:173 */     return this.fechaEmision;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setFechaEmision(Date fechaEmision)
/* 100:    */   {
/* 101:183 */     this.fechaEmision = fechaEmision;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getNumero()
/* 105:    */   {
/* 106:192 */     return this.numero;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setNumero(String numero)
/* 110:    */   {
/* 111:202 */     this.numero = numero;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String getIdentificacionProveedor()
/* 115:    */   {
/* 116:211 */     return this.identificacionProveedor;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setIdentificacionProveedor(String identificacionProveedor)
/* 120:    */   {
/* 121:221 */     this.identificacionProveedor = identificacionProveedor;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public String getDescripcion()
/* 125:    */   {
/* 126:230 */     return this.descripcion;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setDescripcion(String descripcion)
/* 130:    */   {
/* 131:240 */     this.descripcion = descripcion;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public BigDecimal getValor1()
/* 135:    */   {
/* 136:249 */     return this.valor1;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setValor1(BigDecimal valor1)
/* 140:    */   {
/* 141:259 */     this.valor1 = valor1;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public BigDecimal getPorcentajeRetencion()
/* 145:    */   {
/* 146:268 */     return this.porcentajeRetencion;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setPorcentajeRetencion(BigDecimal porcentajeRetencion)
/* 150:    */   {
/* 151:278 */     this.porcentajeRetencion = porcentajeRetencion;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public BigDecimal getValorRetencion()
/* 155:    */   {
/* 156:287 */     return this.valorRetencion;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setValorRetencion(BigDecimal valorRetencion)
/* 160:    */   {
/* 161:297 */     this.valorRetencion = valorRetencion;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public String getCodigo()
/* 165:    */   {
/* 166:306 */     return this.codigo;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public void setCodigo(String codigo)
/* 170:    */   {
/* 171:316 */     this.codigo = codigo;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public String getTipoRetencion()
/* 175:    */   {
/* 176:325 */     return this.tipoRetencion;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void setTipoRetencion(String tipoRetencion)
/* 180:    */   {
/* 181:335 */     this.tipoRetencion = tipoRetencion;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public String getNumeroRetencion()
/* 185:    */   {
/* 186:344 */     return this.numeroRetencion;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void setNumeroRetencion(String numeroRetencion)
/* 190:    */   {
/* 191:354 */     this.numeroRetencion = numeroRetencion;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public ReporteGeneralRetencion getReporteGeneralRetencion()
/* 195:    */   {
/* 196:363 */     return this.reporteGeneralRetencion;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public void setReporteGeneralRetencion(ReporteGeneralRetencion reporteGeneralRetencion)
/* 200:    */   {
/* 201:373 */     this.reporteGeneralRetencion = reporteGeneralRetencion;
/* 202:    */   }
/* 203:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.clases.ReporteRetencionesResumido
 * JD-Core Version:    0.7.0.1
 */