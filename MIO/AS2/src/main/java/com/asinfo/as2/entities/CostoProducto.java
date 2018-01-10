/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.listener.CostoProductoListener;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.Calendar;
/*   6:    */ import java.util.Date;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.EntityListeners;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.JoinColumn;
/*  15:    */ import javax.persistence.ManyToOne;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.persistence.Temporal;
/*  19:    */ import javax.persistence.TemporalType;
/*  20:    */ import javax.persistence.Transient;
/*  21:    */ import javax.validation.constraints.NotNull;
/*  22:    */ 
/*  23:    */ @Entity
/*  24:    */ @Table(name="costo_producto", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_bodega", "id_producto", "fecha"})})
/*  25:    */ @EntityListeners({CostoProductoListener.class})
/*  26:    */ public class CostoProducto
/*  27:    */   extends EntidadBase
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = 1L;
/*  30:    */   @Id
/*  31:    */   @TableGenerator(name="costo_producto", initialValue=0, allocationSize=50)
/*  32:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="costo_producto")
/*  33:    */   @Column(name="id_costo_producto", unique=true, nullable=false)
/*  34:    */   private int idCostoProducto;
/*  35:    */   @Column(name="id_organizacion", nullable=false)
/*  36:    */   private int idOrganizacion;
/*  37:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  38:    */   @JoinColumn(name="id_producto", nullable=false)
/*  39:    */   @NotNull
/*  40:    */   private Producto producto;
/*  41:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  42:    */   @JoinColumn(name="id_bodega", nullable=true)
/*  43:    */   private Bodega bodega;
/*  44:    */   @Temporal(TemporalType.DATE)
/*  45:    */   @Column(name="fecha", nullable=false)
/*  46:    */   private Date fecha;
/*  47:    */   @Column(name="anio", nullable=false)
/*  48:    */   private int anio;
/*  49:    */   @Column(name="mes", nullable=false)
/*  50:    */   private int mes;
/*  51:    */   @Column(name="saldo", nullable=false, precision=18, scale=4)
/*  52:    */   private BigDecimal saldo;
/*  53:    */   @Column(name="costo", nullable=false, precision=18, scale=4)
/*  54:    */   private BigDecimal costo;
/*  55:    */   @Column(name="indicador_saldo_inicial", nullable=false)
/*  56:    */   private boolean indicadorSaldoInicial;
/*  57:    */   @Transient
/*  58:    */   private int idProducto;
/*  59:    */   
/*  60:    */   public CostoProducto(int idProducto, Date fecha)
/*  61:    */   {
/*  62:104 */     this.idProducto = idProducto;
/*  63:105 */     this.fecha = fecha;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public CostoProducto() {}
/*  67:    */   
/*  68:    */   public CostoProducto(int idOrganizacion, Producto producto, Date fecha, BigDecimal saldo, BigDecimal costo)
/*  69:    */   {
/*  70:120 */     this.idOrganizacion = idOrganizacion;
/*  71:121 */     this.producto = producto;
/*  72:122 */     setFecha(fecha);
/*  73:123 */     this.saldo = saldo;
/*  74:124 */     this.costo = costo;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public CostoProducto(int idOrganizacion, Producto producto, Date fecha, BigDecimal saldo, BigDecimal costo, Bodega bodega)
/*  78:    */   {
/*  79:128 */     this(idOrganizacion, producto, fecha, saldo, costo);
/*  80:129 */     setBodega(bodega);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean isAuditable()
/*  84:    */   {
/*  85:139 */     return false;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public int getIdCostoProducto()
/*  89:    */   {
/*  90:148 */     return this.idCostoProducto;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setIdCostoProducto(int idCostoProducto)
/*  94:    */   {
/*  95:158 */     this.idCostoProducto = idCostoProducto;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Producto getProducto()
/*  99:    */   {
/* 100:167 */     return this.producto;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setProducto(Producto producto)
/* 104:    */   {
/* 105:177 */     this.producto = producto;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Date getFecha()
/* 109:    */   {
/* 110:186 */     return this.fecha;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setFecha(Date fecha)
/* 114:    */   {
/* 115:196 */     this.fecha = fecha;
/* 116:    */     
/* 117:198 */     Calendar cFecha = Calendar.getInstance();
/* 118:199 */     cFecha.setTime(this.fecha);
/* 119:    */     
/* 120:201 */     setAnio(cFecha.get(1));
/* 121:202 */     setMes(cFecha.get(2) + 1);
/* 122:    */   }
/* 123:    */   
/* 124:    */   public BigDecimal getCosto()
/* 125:    */   {
/* 126:211 */     if (this.costo == null) {
/* 127:212 */       this.costo = BigDecimal.ZERO;
/* 128:    */     }
/* 129:214 */     return this.costo;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setCosto(BigDecimal costo)
/* 133:    */   {
/* 134:224 */     this.costo = costo;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public int getIdOrganizacion()
/* 138:    */   {
/* 139:231 */     return this.idOrganizacion;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setIdOrganizacion(int idOrganizacion)
/* 143:    */   {
/* 144:239 */     this.idOrganizacion = idOrganizacion;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public BigDecimal getSaldo()
/* 148:    */   {
/* 149:246 */     return this.saldo;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setSaldo(BigDecimal saldo)
/* 153:    */   {
/* 154:254 */     this.saldo = saldo;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public boolean isIndicadorSaldoInicial()
/* 158:    */   {
/* 159:261 */     return this.indicadorSaldoInicial;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setIndicadorSaldoInicial(boolean indicadorSaldoInicial)
/* 163:    */   {
/* 164:269 */     this.indicadorSaldoInicial = indicadorSaldoInicial;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public int getAnio()
/* 168:    */   {
/* 169:276 */     return this.anio;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setAnio(int anio)
/* 173:    */   {
/* 174:284 */     this.anio = anio;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public int getMes()
/* 178:    */   {
/* 179:291 */     return this.mes;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public void setMes(int mes)
/* 183:    */   {
/* 184:299 */     this.mes = mes;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public int getIdProducto()
/* 188:    */   {
/* 189:308 */     return this.idProducto;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setIdProducto(int idProducto)
/* 193:    */   {
/* 194:318 */     this.idProducto = idProducto;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public Bodega getBodega()
/* 198:    */   {
/* 199:322 */     return this.bodega;
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void setBodega(Bodega bodega)
/* 203:    */   {
/* 204:326 */     this.bodega = bodega;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public int getId()
/* 208:    */   {
/* 209:331 */     return this.idCostoProducto;
/* 210:    */   }
/* 211:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CostoProducto
 * JD-Core Version:    0.7.0.1
 */