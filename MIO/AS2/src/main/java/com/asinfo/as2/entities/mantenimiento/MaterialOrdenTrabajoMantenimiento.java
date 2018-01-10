/*   1:    */ package com.asinfo.as2.entities.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Bodega;
/*   4:    */ import com.asinfo.as2.entities.DestinoCosto;
/*   5:    */ import com.asinfo.as2.entities.EntidadBase;
/*   6:    */ import com.asinfo.as2.entities.Lote;
/*   7:    */ import com.asinfo.as2.entities.Producto;
/*   8:    */ import java.io.Serializable;
/*   9:    */ import java.math.BigDecimal;
/*  10:    */ import javax.persistence.Column;
/*  11:    */ import javax.persistence.Entity;
/*  12:    */ import javax.persistence.FetchType;
/*  13:    */ import javax.persistence.GeneratedValue;
/*  14:    */ import javax.persistence.GenerationType;
/*  15:    */ import javax.persistence.Id;
/*  16:    */ import javax.persistence.JoinColumn;
/*  17:    */ import javax.persistence.ManyToOne;
/*  18:    */ import javax.persistence.Table;
/*  19:    */ import javax.persistence.TableGenerator;
/*  20:    */ import javax.validation.constraints.Digits;
/*  21:    */ import javax.validation.constraints.Min;
/*  22:    */ import javax.validation.constraints.NotNull;
/*  23:    */ 
/*  24:    */ @Entity
/*  25:    */ @Table(name="material_orden_trabajo_mantenimiento")
/*  26:    */ public class MaterialOrdenTrabajoMantenimiento
/*  27:    */   extends EntidadBase
/*  28:    */   implements Serializable
/*  29:    */ {
/*  30:    */   private static final long serialVersionUID = 1L;
/*  31:    */   @Id
/*  32:    */   @TableGenerator(name="material_orden_trabajo_mantenimiento", initialValue=0, allocationSize=50)
/*  33:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="material_orden_trabajo_mantenimiento")
/*  34:    */   @Column(name="id_material_orden_trabajo_mantenimiento")
/*  35:    */   private int idMaterialOrdenTrabajoMantenimiento;
/*  36:    */   @Column(name="id_organizacion")
/*  37:    */   private int idOrganizacion;
/*  38:    */   @Column(name="id_sucursal")
/*  39:    */   private int idSucursal;
/*  40:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  41:    */   @JoinColumn(name="id_orden_trabajo_mantenimiento", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private OrdenTrabajoMantenimiento ordenTrabajoMantenimiento;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_material", nullable=false)
/*  46:    */   @NotNull
/*  47:    */   private Producto material;
/*  48:    */   @Column(name="cantidad_requerida", nullable=false, precision=12, scale=2)
/*  49:    */   @Digits(integer=10, fraction=2)
/*  50:    */   @Min(0L)
/*  51:    */   @NotNull
/*  52: 64 */   private BigDecimal cantidadRequerida = BigDecimal.ZERO;
/*  53:    */   @Column(name="cantidad_despachada", nullable=false, precision=12, scale=2)
/*  54:    */   @Digits(integer=10, fraction=2)
/*  55:    */   @Min(0L)
/*  56:    */   @NotNull
/*  57: 70 */   private BigDecimal cantidadDespachada = BigDecimal.ZERO;
/*  58:    */   @Column(name="cantidad_devuelta", nullable=false, precision=12, scale=2)
/*  59:    */   @Digits(integer=10, fraction=2)
/*  60:    */   @Min(0L)
/*  61:    */   @NotNull
/*  62: 76 */   private BigDecimal cantidadDevuelta = BigDecimal.ZERO;
/*  63:    */   private transient DestinoCosto destinoCosto;
/*  64:    */   private transient Bodega bodegaOrigen;
/*  65:    */   private transient Lote lote;
/*  66:    */   
/*  67:    */   public int getId()
/*  68:    */   {
/*  69: 97 */     return this.idMaterialOrdenTrabajoMantenimiento;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getIdMaterialOrdenTrabajoMantenimiento()
/*  73:    */   {
/*  74:101 */     return this.idMaterialOrdenTrabajoMantenimiento;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setIdMaterialOrdenTrabajoMantenimiento(int idMaterialOrdenTrabajoMantenimiento)
/*  78:    */   {
/*  79:105 */     this.idMaterialOrdenTrabajoMantenimiento = idMaterialOrdenTrabajoMantenimiento;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public int getIdOrganizacion()
/*  83:    */   {
/*  84:109 */     return this.idOrganizacion;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setIdOrganizacion(int idOrganizacion)
/*  88:    */   {
/*  89:113 */     this.idOrganizacion = idOrganizacion;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public int getIdSucursal()
/*  93:    */   {
/*  94:117 */     return this.idSucursal;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setIdSucursal(int idSucursal)
/*  98:    */   {
/*  99:121 */     this.idSucursal = idSucursal;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public OrdenTrabajoMantenimiento getOrdenTrabajoMantenimiento()
/* 103:    */   {
/* 104:125 */     return this.ordenTrabajoMantenimiento;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setOrdenTrabajoMantenimiento(OrdenTrabajoMantenimiento ordenTrabajoMantenimiento)
/* 108:    */   {
/* 109:129 */     this.ordenTrabajoMantenimiento = ordenTrabajoMantenimiento;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public Producto getMaterial()
/* 113:    */   {
/* 114:133 */     return this.material;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setMaterial(Producto material)
/* 118:    */   {
/* 119:137 */     this.material = material;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public BigDecimal getCantidadRequerida()
/* 123:    */   {
/* 124:141 */     return this.cantidadRequerida;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setCantidadRequerida(BigDecimal cantidadRequerida)
/* 128:    */   {
/* 129:145 */     this.cantidadRequerida = cantidadRequerida;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public BigDecimal getCantidadDespachada()
/* 133:    */   {
/* 134:149 */     return this.cantidadDespachada;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setCantidadDespachada(BigDecimal cantidadDespachada)
/* 138:    */   {
/* 139:153 */     this.cantidadDespachada = cantidadDespachada;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public BigDecimal getCantidadDevuelta()
/* 143:    */   {
/* 144:157 */     return this.cantidadDevuelta;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setCantidadDevuelta(BigDecimal cantidadDevuelta)
/* 148:    */   {
/* 149:161 */     this.cantidadDevuelta = cantidadDevuelta;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public BigDecimal getCantidadConsumida()
/* 153:    */   {
/* 154:165 */     return getCantidadDespachada().subtract(getCantidadDevuelta());
/* 155:    */   }
/* 156:    */   
/* 157:    */   public BigDecimal getCantidadPorDespachar()
/* 158:    */   {
/* 159:169 */     return getCantidadRequerida().subtract(getCantidadConsumida());
/* 160:    */   }
/* 161:    */   
/* 162:    */   public DestinoCosto getDestinoCosto()
/* 163:    */   {
/* 164:173 */     return this.destinoCosto;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setDestinoCosto(DestinoCosto destinoCosto)
/* 168:    */   {
/* 169:177 */     this.destinoCosto = destinoCosto;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public Bodega getBodegaOrigen()
/* 173:    */   {
/* 174:181 */     return this.bodegaOrigen;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public void setBodegaOrigen(Bodega bodegaOrigen)
/* 178:    */   {
/* 179:185 */     this.bodegaOrigen = bodegaOrigen;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public Lote getLote()
/* 183:    */   {
/* 184:189 */     return this.lote;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public void setLote(Lote lote)
/* 188:    */   {
/* 189:193 */     this.lote = lote;
/* 190:    */   }
/* 191:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.MaterialOrdenTrabajoMantenimiento
 * JD-Core Version:    0.7.0.1
 */