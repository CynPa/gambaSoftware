/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import java.math.BigDecimal;
/*   6:    */ import java.math.RoundingMode;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import javax.persistence.Column;
/*  10:    */ import javax.persistence.Entity;
/*  11:    */ import javax.persistence.FetchType;
/*  12:    */ import javax.persistence.GeneratedValue;
/*  13:    */ import javax.persistence.GenerationType;
/*  14:    */ import javax.persistence.Id;
/*  15:    */ import javax.persistence.JoinColumn;
/*  16:    */ import javax.persistence.ManyToOne;
/*  17:    */ import javax.persistence.OneToMany;
/*  18:    */ import javax.persistence.Table;
/*  19:    */ import javax.persistence.TableGenerator;
/*  20:    */ import javax.persistence.Transient;
/*  21:    */ import javax.validation.constraints.DecimalMin;
/*  22:    */ import javax.validation.constraints.Digits;
/*  23:    */ import javax.validation.constraints.Min;
/*  24:    */ import javax.validation.constraints.NotNull;
/*  25:    */ 
/*  26:    */ @Entity
/*  27:    */ @Table(name="detalle_orden_salida_material_orden_fabricacion", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_detalle_orden_salida_material", "id_orden_fabricacion"})})
/*  28:    */ public class DetalleOrdenSalidaMaterialOrdenFabricacion
/*  29:    */   extends EntidadBase
/*  30:    */   implements Serializable
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 1L;
/*  33:    */   @Id
/*  34:    */   @TableGenerator(name="detalle_orden_salida_material_orden_fabricacion", initialValue=0, allocationSize=50)
/*  35:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_orden_salida_material_orden_fabricacion")
/*  36:    */   @Column(name="id_detalle_orden_salida_material_orden_fabricacion")
/*  37:    */   private int idDetalleOrdenSalidaMaterialOrdenFabricacion;
/*  38:    */   @Column(name="id_organizacion", nullable=false)
/*  39:    */   @NotNull
/*  40:    */   private int idOrganizacion;
/*  41:    */   @Column(name="id_sucursal", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private int idSucursal;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_detalle_orden_salida_material", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial;
/*  48:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  49:    */   @JoinColumn(name="id_orden_fabricacion", nullable=false)
/*  50:    */   @NotNull
/*  51:    */   private OrdenFabricacion ordenFabricacion;
/*  52:    */   @NotNull
/*  53:    */   @Column(name="cantidad", nullable=false, precision=12, scale=2)
/*  54:    */   @Digits(integer=10, fraction=4)
/*  55:    */   @DecimalMin("0.00")
/*  56: 71 */   private BigDecimal cantidad = BigDecimal.ZERO;
/*  57:    */   @Transient
/*  58: 77 */   private BigDecimal cantidadSegunFabricacion = BigDecimal.ZERO;
/*  59:    */   @NotNull
/*  60:    */   @Column(name="cantidad_utilizada", nullable=false, precision=12, scale=2)
/*  61:    */   @Digits(integer=10, fraction=2)
/*  62:    */   @Min(0L)
/*  63: 80 */   private BigDecimal cantidadUtilizada = BigDecimal.ZERO;
/*  64:    */   @Column(name="cantidad_desecho", nullable=true, precision=12, scale=2)
/*  65:    */   @Digits(integer=10, fraction=2)
/*  66:    */   @Min(0L)
/*  67: 86 */   private BigDecimal cantidadDesecho = BigDecimal.ZERO;
/*  68:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleOrdenSalidaMaterialOrdenFabricacion")
/*  69: 91 */   private List<LecturaBalanza> listaLecturaBalanza = new ArrayList();
/*  70:    */   @Transient
/*  71: 95 */   private BigDecimal cantidadOriginalRepartir = BigDecimal.ZERO;
/*  72:    */   
/*  73:    */   public int getId()
/*  74:    */   {
/*  75:100 */     return this.idDetalleOrdenSalidaMaterialOrdenFabricacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIdDetalleOrdenSalidaMaterialOrdenFabricacion()
/*  79:    */   {
/*  80:108 */     return this.idDetalleOrdenSalidaMaterialOrdenFabricacion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdDetalleOrdenSalidaMaterialOrdenFabricacion(int idDetalleOrdenSalidaMaterialOrdenFabricacion)
/*  84:    */   {
/*  85:112 */     this.idDetalleOrdenSalidaMaterialOrdenFabricacion = idDetalleOrdenSalidaMaterialOrdenFabricacion;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public DetalleOrdenSalidaMaterial getDetalleOrdenSalidaMaterial()
/*  89:    */   {
/*  90:116 */     return this.detalleOrdenSalidaMaterial;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setDetalleOrdenSalidaMaterial(DetalleOrdenSalidaMaterial detalleOrdenSalidaMaterial)
/*  94:    */   {
/*  95:120 */     this.detalleOrdenSalidaMaterial = detalleOrdenSalidaMaterial;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public OrdenFabricacion getOrdenFabricacion()
/*  99:    */   {
/* 100:124 */     return this.ordenFabricacion;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setOrdenFabricacion(OrdenFabricacion ordenFabricacion)
/* 104:    */   {
/* 105:128 */     this.ordenFabricacion = ordenFabricacion;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public BigDecimal getCantidad()
/* 109:    */   {
/* 110:132 */     return this.cantidad;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setCantidad(BigDecimal cantidad)
/* 114:    */   {
/* 115:136 */     this.cantidad = cantidad;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public BigDecimal getCantidadUtilizada()
/* 119:    */   {
/* 120:140 */     return this.cantidadUtilizada;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setCantidadUtilizada(BigDecimal cantidadUtilizada)
/* 124:    */   {
/* 125:144 */     this.cantidadUtilizada = cantidadUtilizada;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public BigDecimal getCantidadSegunFabricacion()
/* 129:    */   {
/* 130:148 */     return this.cantidadSegunFabricacion;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setCantidadSegunFabricacion(BigDecimal cantidadSegunFabricacion)
/* 134:    */   {
/* 135:152 */     this.cantidadSegunFabricacion = cantidadSegunFabricacion;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public int getIdOrganizacion()
/* 139:    */   {
/* 140:156 */     return this.idOrganizacion;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setIdOrganizacion(int idOrganizacion)
/* 144:    */   {
/* 145:160 */     this.idOrganizacion = idOrganizacion;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public int getIdSucursal()
/* 149:    */   {
/* 150:164 */     return this.idSucursal;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setIdSucursal(int idSucursal)
/* 154:    */   {
/* 155:168 */     this.idSucursal = idSucursal;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public BigDecimal getCantidadOriginalRepartir()
/* 159:    */   {
/* 160:172 */     return this.cantidadOriginalRepartir;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setCantidadOriginalRepartir(BigDecimal cantidadOriginalRepartir)
/* 164:    */   {
/* 165:176 */     this.cantidadOriginalRepartir = cantidadOriginalRepartir;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public BigDecimal getCantidadUtilizadaSegunSolicitud()
/* 169:    */   {
/* 170:181 */     if (this.detalleOrdenSalidaMaterial.getCantidad().compareTo(BigDecimal.ZERO) == 0) {
/* 171:182 */       return BigDecimal.ZERO;
/* 172:    */     }
/* 173:184 */     BigDecimal proporcionSolicitud = this.cantidad.divide(this.detalleOrdenSalidaMaterial.getCantidad(), 10, RoundingMode.HALF_UP);
/* 174:185 */     if (this.detalleOrdenSalidaMaterial.isIndicadorConsumoDirecto())
/* 175:    */     {
/* 176:187 */       if (this.ordenFabricacion.getCantidadFabricada().compareTo(BigDecimal.ZERO) == 0) {
/* 177:188 */         return BigDecimal.ZERO;
/* 178:    */       }
/* 179:191 */       BigDecimal cantidadAdicional = this.detalleOrdenSalidaMaterial.getCantidadAdicional().multiply(proporcionSolicitud);
/* 180:192 */       BigDecimal proporcionFabricada = this.ordenFabricacion.getCantidadFabricada().divide(this.ordenFabricacion.getCantidad(), 10, RoundingMode.HALF_UP);
/* 181:193 */       BigDecimal cantidadSegunRecetaFabricada = this.cantidad.multiply(proporcionFabricada);
/* 182:194 */       return cantidadSegunRecetaFabricada.add(cantidadAdicional).setScale(this.detalleOrdenSalidaMaterial.getUnidad().getNumeroDecimales().intValue(), RoundingMode.HALF_UP);
/* 183:    */     }
/* 184:199 */     return this.detalleOrdenSalidaMaterial.getCantidadUtilizadaSinDesecho().multiply(proporcionSolicitud).setScale(this.detalleOrdenSalidaMaterial.getUnidad().getNumeroDecimales().intValue(), RoundingMode.HALF_UP);
/* 185:    */   }
/* 186:    */   
/* 187:    */   public BigDecimal getCantidadDesecho()
/* 188:    */   {
/* 189:203 */     if (this.cantidadDesecho == null) {
/* 190:204 */       this.cantidadDesecho = BigDecimal.ZERO;
/* 191:    */     }
/* 192:206 */     return this.cantidadDesecho;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setCantidadDesecho(BigDecimal cantidadDesecho)
/* 196:    */   {
/* 197:210 */     this.cantidadDesecho = cantidadDesecho;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public List<LecturaBalanza> getListaLecturaBalanza()
/* 201:    */   {
/* 202:214 */     return this.listaLecturaBalanza;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setListaLecturaBalanza(List<LecturaBalanza> listaLecturaBalanza)
/* 206:    */   {
/* 207:218 */     this.listaLecturaBalanza = listaLecturaBalanza;
/* 208:    */   }
/* 209:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleOrdenSalidaMaterialOrdenFabricacion
 * JD-Core Version:    0.7.0.1
 */