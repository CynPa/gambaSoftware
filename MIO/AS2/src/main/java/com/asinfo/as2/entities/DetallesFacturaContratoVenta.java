/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Date;
/*   7:    */ import java.util.List;
/*   8:    */ import javax.persistence.Column;
/*   9:    */ import javax.persistence.Entity;
/*  10:    */ import javax.persistence.FetchType;
/*  11:    */ import javax.persistence.GeneratedValue;
/*  12:    */ import javax.persistence.GenerationType;
/*  13:    */ import javax.persistence.Id;
/*  14:    */ import javax.persistence.JoinColumn;
/*  15:    */ import javax.persistence.ManyToOne;
/*  16:    */ import javax.persistence.OneToMany;
/*  17:    */ import javax.persistence.Table;
/*  18:    */ import javax.persistence.TableGenerator;
/*  19:    */ import javax.persistence.Temporal;
/*  20:    */ import javax.persistence.TemporalType;
/*  21:    */ import javax.validation.constraints.Digits;
/*  22:    */ import javax.validation.constraints.Min;
/*  23:    */ import javax.validation.constraints.NotNull;
/*  24:    */ import javax.validation.constraints.Size;
/*  25:    */ 
/*  26:    */ @Entity
/*  27:    */ @Table(name="detalles_factura_contrato_venta")
/*  28:    */ public class DetallesFacturaContratoVenta
/*  29:    */   extends EntidadBase
/*  30:    */   implements Serializable
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = -1847869211434882233L;
/*  33:    */   @Id
/*  34:    */   @TableGenerator(name="detalles_factura_contrato_venta", initialValue=0, allocationSize=50)
/*  35:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalles_factura_contrato_venta")
/*  36:    */   @Column(name="id_detalles_factura_contrato_venta")
/*  37:    */   private int idDetallesFacturaContratoVenta;
/*  38:    */   @Column(name="id_sucursal", nullable=false)
/*  39:    */   @NotNull
/*  40:    */   private int idSucursal;
/*  41:    */   @Column(name="id_organizacion", nullable=false)
/*  42:    */   @NotNull
/*  43:    */   private int idOrganizacion;
/*  44:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  45:    */   @JoinColumn(name="id_contrato_venta", nullable=true)
/*  46:    */   private ContratoVenta contratoVenta;
/*  47:    */   @Temporal(TemporalType.DATE)
/*  48:    */   @Column(name="fecha", nullable=false, length=23)
/*  49:    */   @NotNull
/*  50:    */   private Date fecha;
/*  51:    */   @Column(name="valor", nullable=false, precision=12, scale=6)
/*  52:    */   @NotNull
/*  53:    */   @Digits(integer=12, fraction=6)
/*  54:    */   @Min(0L)
/*  55: 63 */   private BigDecimal valor = BigDecimal.ZERO;
/*  56:    */   @Column(name="descripcion", nullable=true, length=400)
/*  57:    */   @Size(max=400)
/*  58:    */   private String descripcion;
/*  59:    */   @OneToMany(mappedBy="detallesFacturaContratoVenta", fetch=FetchType.LAZY)
/*  60: 73 */   private List<ContratoVentaFacturaContratoVenta> listaContratoVentaFacturaContratoVenta = new ArrayList();
/*  61:    */   @Column(name="indicador_facturado", nullable=true)
/*  62:    */   private Boolean indicadorFacturado;
/*  63:    */   @Column(name="numero_cuota", nullable=true)
/*  64:    */   @Min(0L)
/*  65:    */   private Integer numeroCuota;
/*  66:    */   @Column(name="codigo_formaPagoSRI", nullable=true)
/*  67:    */   private String codigoFormaPagoSRI;
/*  68:    */   
/*  69:    */   public BigDecimal getTotalDetalleFacturaContratoVenta()
/*  70:    */   {
/*  71: 89 */     BigDecimal total = BigDecimal.ZERO;
/*  72: 90 */     for (ContratoVentaFacturaContratoVenta cvfcv : getListaContratoVentaFacturaContratoVenta()) {
/*  73: 91 */       total = total.add(cvfcv.getValor());
/*  74:    */     }
/*  75: 94 */     return total;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getId()
/*  79:    */   {
/*  80: 99 */     return this.idDetallesFacturaContratoVenta;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getIdDetallesFacturaContratoVenta()
/*  84:    */   {
/*  85:103 */     return this.idDetallesFacturaContratoVenta;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdDetallesFacturaContratoVenta(int idDetallesFacturaContratoVenta)
/*  89:    */   {
/*  90:107 */     this.idDetallesFacturaContratoVenta = idDetallesFacturaContratoVenta;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getDescripcion()
/*  94:    */   {
/*  95:111 */     return this.descripcion;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setDescripcion(String descripcion)
/*  99:    */   {
/* 100:115 */     this.descripcion = descripcion;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Date getFecha()
/* 104:    */   {
/* 105:119 */     return this.fecha;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setFecha(Date fecha)
/* 109:    */   {
/* 110:123 */     this.fecha = fecha;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public int getIdSucursal()
/* 114:    */   {
/* 115:127 */     return this.idSucursal;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setIdSucursal(int idSucursal)
/* 119:    */   {
/* 120:131 */     this.idSucursal = idSucursal;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public int getIdOrganizacion()
/* 124:    */   {
/* 125:135 */     return this.idOrganizacion;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setIdOrganizacion(int idOrganizacion)
/* 129:    */   {
/* 130:139 */     this.idOrganizacion = idOrganizacion;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public BigDecimal getValor()
/* 134:    */   {
/* 135:143 */     return this.valor;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setValor(BigDecimal valor)
/* 139:    */   {
/* 140:147 */     this.valor = valor;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public ContratoVenta getContratoVenta()
/* 144:    */   {
/* 145:151 */     return this.contratoVenta;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setContratoVenta(ContratoVenta contratoVenta)
/* 149:    */   {
/* 150:155 */     this.contratoVenta = contratoVenta;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public List<ContratoVentaFacturaContratoVenta> getListaContratoVentaFacturaContratoVenta()
/* 154:    */   {
/* 155:159 */     return this.listaContratoVentaFacturaContratoVenta;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setListaContratoVentaFacturaContratoVenta(List<ContratoVentaFacturaContratoVenta> listaContratoVentaFacturaContratoVenta)
/* 159:    */   {
/* 160:163 */     this.listaContratoVentaFacturaContratoVenta = listaContratoVentaFacturaContratoVenta;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public boolean isIndicadorFacturado()
/* 164:    */   {
/* 165:167 */     if (this.indicadorFacturado == null) {
/* 166:168 */       this.indicadorFacturado = Boolean.valueOf(false);
/* 167:    */     }
/* 168:170 */     return this.indicadorFacturado.booleanValue();
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setIndicadorFacturado(boolean indicadorFacturado)
/* 172:    */   {
/* 173:174 */     this.indicadorFacturado = Boolean.valueOf(indicadorFacturado);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public Integer getNumeroCuota()
/* 177:    */   {
/* 178:178 */     return this.numeroCuota;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setNumeroCuota(Integer numeroCuota)
/* 182:    */   {
/* 183:182 */     this.numeroCuota = numeroCuota;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public String getCodigoFormaPagoSRI()
/* 187:    */   {
/* 188:186 */     return this.codigoFormaPagoSRI;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setCodigoFormaPagoSRI(String codigoFormaPagoSRI)
/* 192:    */   {
/* 193:190 */     this.codigoFormaPagoSRI = codigoFormaPagoSRI;
/* 194:    */   }
/* 195:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetallesFacturaContratoVenta
 * JD-Core Version:    0.7.0.1
 */