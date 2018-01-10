/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.OneToMany;
/*  16:    */ import javax.persistence.Table;
/*  17:    */ import javax.persistence.TableGenerator;
/*  18:    */ import javax.validation.constraints.Digits;
/*  19:    */ import javax.validation.constraints.NotNull;
/*  20:    */ import javax.validation.constraints.Size;
/*  21:    */ 
/*  22:    */ @Entity
/*  23:    */ @Table(name="detalle_compra_caja_chica")
/*  24:    */ public class DetalleCompraCajaChica
/*  25:    */   extends EntidadBase
/*  26:    */   implements Serializable
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = 1L;
/*  29:    */   @Id
/*  30:    */   @TableGenerator(name="detalle_compra_caja_chica", initialValue=0, allocationSize=50)
/*  31:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_compra_caja_chica")
/*  32:    */   @Column(name="id_detalle_compra_caja_chica", unique=true, nullable=false)
/*  33:    */   private int idDetalleCompraCajaChica;
/*  34:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  35:    */   @JoinColumn(name="id_compra_caja_chica", nullable=true)
/*  36:    */   private CompraCajaChica compraCajaChica;
/*  37:    */   @ManyToOne(fetch=FetchType.EAGER)
/*  38:    */   @JoinColumn(name="id_cuenta_contable", nullable=false)
/*  39:    */   @NotNull
/*  40:    */   private CuentaContable cuentaContable;
/*  41:    */   @Column(name="id_organizacion", nullable=false)
/*  42:    */   private int idOrganizacion;
/*  43:    */   @Column(name="id_sucursal", nullable=false)
/*  44:    */   private int idSucursal;
/*  45:    */   @Column(name="valor", precision=12, scale=2)
/*  46:    */   @Digits(integer=12, fraction=2)
/*  47: 56 */   private BigDecimal valor = BigDecimal.ZERO;
/*  48:    */   @Column(name="descripcion", length=200)
/*  49:    */   @Size(max=200)
/*  50:    */   private String descripcion;
/*  51:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  52:    */   @JoinColumn(name="id_dimension_contable1")
/*  53:    */   private DimensionContable dimensionContable1;
/*  54:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  55:    */   @JoinColumn(name="id_dimension_contable2")
/*  56:    */   private DimensionContable dimensionContable2;
/*  57:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  58:    */   @JoinColumn(name="id_dimension_contable3")
/*  59:    */   private DimensionContable dimensionContable3;
/*  60:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  61:    */   @JoinColumn(name="id_dimension_contable4")
/*  62:    */   private DimensionContable dimensionContable4;
/*  63:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  64:    */   @JoinColumn(name="id_dimension_contable5")
/*  65:    */   private DimensionContable dimensionContable5;
/*  66:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="detalleCompraCajaChica")
/*  67: 84 */   private List<DetalleCompraCajaChicaCentroCosto> listaDetalleCompraCajaChicaCentroCosto = new ArrayList();
/*  68:    */   
/*  69:    */   public int getIdDetalleCompraCajaChica()
/*  70:    */   {
/*  71: 97 */     return this.idDetalleCompraCajaChica;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setIdDetalleCompraCajaChica(int idDetalleCompraCajaChica)
/*  75:    */   {
/*  76:107 */     this.idDetalleCompraCajaChica = idDetalleCompraCajaChica;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public CuentaContable getCuentaContable()
/*  80:    */   {
/*  81:116 */     return this.cuentaContable;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setCuentaContable(CuentaContable cuentaContable)
/*  85:    */   {
/*  86:126 */     this.cuentaContable = cuentaContable;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public int getIdOrganizacion()
/*  90:    */   {
/*  91:135 */     return this.idOrganizacion;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setIdOrganizacion(int idOrganizacion)
/*  95:    */   {
/*  96:145 */     this.idOrganizacion = idOrganizacion;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int getIdSucursal()
/* 100:    */   {
/* 101:154 */     return this.idSucursal;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setIdSucursal(int idSucursal)
/* 105:    */   {
/* 106:164 */     this.idSucursal = idSucursal;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public BigDecimal getValor()
/* 110:    */   {
/* 111:173 */     return this.valor;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void setValor(BigDecimal valor)
/* 115:    */   {
/* 116:183 */     this.valor = valor;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public String getDescripcion()
/* 120:    */   {
/* 121:192 */     return this.descripcion;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void setDescripcion(String descripcion)
/* 125:    */   {
/* 126:202 */     this.descripcion = descripcion;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public CompraCajaChica getCompraCajaChica()
/* 130:    */   {
/* 131:206 */     return this.compraCajaChica;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void setCompraCajaChica(CompraCajaChica compraCajaChica)
/* 135:    */   {
/* 136:210 */     this.compraCajaChica = compraCajaChica;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public List<DetalleCompraCajaChicaCentroCosto> getListaDetalleCompraCajaChicaCentroCosto()
/* 140:    */   {
/* 141:214 */     return this.listaDetalleCompraCajaChicaCentroCosto;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public void setListaDetalleCompraCajaChicaCentroCosto(List<DetalleCompraCajaChicaCentroCosto> listaDetalleCompraCajaChicaCentroCosto)
/* 145:    */   {
/* 146:218 */     this.listaDetalleCompraCajaChicaCentroCosto = listaDetalleCompraCajaChicaCentroCosto;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public DimensionContable getDimensionContable1()
/* 150:    */   {
/* 151:222 */     return this.dimensionContable1;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void setDimensionContable1(DimensionContable dimensionContable1)
/* 155:    */   {
/* 156:226 */     this.dimensionContable1 = dimensionContable1;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public DimensionContable getDimensionContable2()
/* 160:    */   {
/* 161:230 */     return this.dimensionContable2;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setDimensionContable2(DimensionContable dimensionContable2)
/* 165:    */   {
/* 166:234 */     this.dimensionContable2 = dimensionContable2;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public DimensionContable getDimensionContable3()
/* 170:    */   {
/* 171:238 */     return this.dimensionContable3;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void setDimensionContable3(DimensionContable dimensionContable3)
/* 175:    */   {
/* 176:242 */     this.dimensionContable3 = dimensionContable3;
/* 177:    */   }
/* 178:    */   
/* 179:    */   public DimensionContable getDimensionContable4()
/* 180:    */   {
/* 181:246 */     return this.dimensionContable4;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void setDimensionContable4(DimensionContable dimensionContable4)
/* 185:    */   {
/* 186:250 */     this.dimensionContable4 = dimensionContable4;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public DimensionContable getDimensionContable5()
/* 190:    */   {
/* 191:254 */     return this.dimensionContable5;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void setDimensionContable5(DimensionContable dimensionContable5)
/* 195:    */   {
/* 196:258 */     this.dimensionContable5 = dimensionContable5;
/* 197:    */   }
/* 198:    */   
/* 199:    */   public int getId()
/* 200:    */   {
/* 201:269 */     return getIdDetalleCompraCajaChica();
/* 202:    */   }
/* 203:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleCompraCajaChica
 * JD-Core Version:    0.7.0.1
 */