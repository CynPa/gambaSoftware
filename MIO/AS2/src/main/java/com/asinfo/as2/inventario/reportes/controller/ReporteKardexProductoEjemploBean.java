/*   1:    */ package com.asinfo.as2.inventario.reportes.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.clases.FactorConversion;
/*   4:    */ import com.asinfo.as2.clases.ReporteInventarioProducto;
/*   5:    */ import com.asinfo.as2.controller.PageController;
/*   6:    */ import com.asinfo.as2.entities.Bodega;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.OrganizacionConfiguracion;
/*   9:    */ import com.asinfo.as2.entities.Producto;
/*  10:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  11:    */ import com.asinfo.as2.entities.Unidad;
/*  12:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  13:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioBodega;
/*  14:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioUnidadConversion;
/*  16:    */ import com.asinfo.as2.inventario.excepciones.ExcepcionAS2Inventario;
/*  17:    */ import com.asinfo.as2.inventario.reportes.servicio.ServicioReporteInventarioProducto;
/*  18:    */ import com.asinfo.as2.util.AppUtil;
/*  19:    */ import java.math.BigDecimal;
/*  20:    */ import java.math.RoundingMode;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.Date;
/*  23:    */ import java.util.List;
/*  24:    */ import javax.ejb.EJB;
/*  25:    */ import javax.faces.bean.ManagedBean;
/*  26:    */ import javax.faces.bean.ViewScoped;
/*  27:    */ import javax.faces.model.SelectItem;
/*  28:    */ import javax.persistence.Temporal;
/*  29:    */ import javax.persistence.TemporalType;
/*  30:    */ import javax.validation.constraints.NotNull;
/*  31:    */ 
/*  32:    */ @ManagedBean
/*  33:    */ @ViewScoped
/*  34:    */ public class ReporteKardexProductoEjemploBean
/*  35:    */   extends PageController
/*  36:    */ {
/*  37:    */   private static final long serialVersionUID = -657564978049733933L;
/*  38:    */   @EJB
/*  39:    */   private ServicioReporteInventarioProducto servicioReporteInventarioProducto;
/*  40:    */   @EJB
/*  41:    */   private ServicioBodega servicioBodega;
/*  42:    */   @EJB
/*  43:    */   private ServicioProducto servicioProducto;
/*  44:    */   @EJB
/*  45:    */   private ServicioUnidadConversion servicioUnidadConversion;
/*  46:    */   private Bodega bodega;
/*  47:    */   @Temporal(TemporalType.DATE)
/*  48:    */   @NotNull
/*  49: 77 */   private Date fechaDesde = new Date();
/*  50:    */   @Temporal(TemporalType.DATE)
/*  51:    */   @NotNull
/*  52: 81 */   private Date fechaHasta = new Date();
/*  53:    */   private List<Producto> listaProducto;
/*  54:    */   private List<Bodega> listaBodega;
/*  55:    */   private List<ReporteInventarioProducto> listaReporteInventarioProducto;
/*  56:    */   
/*  57:    */   private static enum enumUnidad
/*  58:    */   {
/*  59: 94 */     STOCK,  VENTA,  ALMACENAMIENTO;
/*  60:    */     
/*  61:    */     private enumUnidad() {}
/*  62:    */   }
/*  63:    */   
/*  64: 96 */   private enumUnidad unidad = enumUnidad.STOCK;
/*  65:    */   private List<SelectItem> listaUnidad;
/*  66:    */   
/*  67:    */   public String procesar()
/*  68:    */   {
/*  69:    */     try
/*  70:    */     {
/*  71:108 */       if (getListaProducto() != null)
/*  72:    */       {
/*  73:110 */         this.listaReporteInventarioProducto = this.servicioReporteInventarioProducto.obtenerMovimientosInventarioProducto(AppUtil.getOrganizacion()
/*  74:111 */           .getId(), this.listaProducto, this.bodega, this.fechaDesde, this.fechaHasta, getUnidad().ordinal(), null, new ArrayList(), AppUtil.getOrganizacion().getOrganizacionConfiguracion().getNumeroAtributos());
/*  75:113 */         for (ReporteInventarioProducto reporteInventarioProducto : this.listaReporteInventarioProducto)
/*  76:    */         {
/*  77:115 */           Producto producto = this.servicioProducto.cargaDetalle(reporteInventarioProducto.getProducto().getId());
/*  78:    */           
/*  79:    */ 
/*  80:118 */           BigDecimal saldoInicial = BigDecimal.ZERO;
/*  81:119 */           BigDecimal costoInicial = BigDecimal.ZERO;
/*  82:    */           
/*  83:121 */           FactorConversion factorConversion = null;
/*  84:122 */           String strMensaje = "";
/*  85:124 */           if (this.unidad.equals(enumUnidad.VENTA))
/*  86:    */           {
/*  87:126 */             factorConversion = this.servicioUnidadConversion.obtenerFactorConversion(producto.getId(), producto.getSubcategoriaProducto()
/*  88:127 */               .getIdSubcategoriaProducto(), producto.getUnidad().getId(), producto.getUnidadVenta().getId());
/*  89:    */             
/*  90:    */ 
/*  91:130 */             strMensaje = producto.getCodigo() + "-" + producto.getNombre() + " (" + producto.getUnidad().getNombre() + "-" + producto.getUnidadVenta().getNombre() + ")";
/*  92:    */           }
/*  93:132 */           else if (this.unidad.equals(enumUnidad.ALMACENAMIENTO))
/*  94:    */           {
/*  95:134 */             factorConversion = this.servicioUnidadConversion.obtenerFactorConversion(producto.getId(), producto.getSubcategoriaProducto()
/*  96:135 */               .getIdSubcategoriaProducto(), producto.getUnidad().getId(), producto.getUnidadAlmacenamiento().getId());
/*  97:    */             
/*  98:137 */             strMensaje = producto.getCodigo() + "-" + producto.getNombre() + " (" + producto.getUnidad().getNombre() + "-" + producto.getUnidadAlmacenamiento().getNombre() + ")";
/*  99:    */           }
/* 100:141 */           if (!this.unidad.equals(enumUnidad.STOCK)) {
/* 101:142 */             if (factorConversion != null)
/* 102:    */             {
/* 103:143 */               if (factorConversion.isIndicadorInverso()) {
/* 104:144 */                 reporteInventarioProducto.setCantidad(reporteInventarioProducto.getCantidad().divide(factorConversion.getFactor(), 4, RoundingMode.HALF_UP));
/* 105:    */               } else {
/* 106:148 */                 reporteInventarioProducto.setCantidad(reporteInventarioProducto.getCantidad().multiply(factorConversion.getFactor()).setScale(4, RoundingMode.HALF_UP));
/* 107:    */               }
/* 108:    */             }
/* 109:    */             else {
/* 110:152 */               throw new ExcepcionAS2Inventario("msg_error_unidad_conversion", strMensaje);
/* 111:    */             }
/* 112:    */           }
/* 113:156 */           saldoInicial = saldoInicial.add(reporteInventarioProducto.getCantidad().multiply(new BigDecimal(reporteInventarioProducto
/* 114:157 */             .getOperacion().intValue())));
/* 115:158 */           costoInicial = costoInicial.add(reporteInventarioProducto.getCosto().multiply(new BigDecimal(reporteInventarioProducto
/* 116:159 */             .getOperacion().intValue())));
/* 117:    */           
/* 118:161 */           reporteInventarioProducto.setCantidadTotal(saldoInicial.setScale(4, RoundingMode.HALF_UP));
/* 119:162 */           reporteInventarioProducto.setCostoTotal(costoInicial.setScale(4, RoundingMode.HALF_UP));
/* 120:    */         }
/* 121:    */       }
/* 122:    */     }
/* 123:    */     catch (ExcepcionAS2 localExcepcionAS21) {}
/* 124:169 */     return "";
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Date getFechaDesde()
/* 128:    */   {
/* 129:178 */     return this.fechaDesde;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setFechaDesde(Date fechaDesde)
/* 133:    */   {
/* 134:188 */     this.fechaDesde = fechaDesde;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Date getFechaHasta()
/* 138:    */   {
/* 139:197 */     return this.fechaHasta;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setFechaHasta(Date fechaHasta)
/* 143:    */   {
/* 144:207 */     this.fechaHasta = fechaHasta;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public Bodega getBodega()
/* 148:    */   {
/* 149:216 */     return this.bodega;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setBodega(Bodega bodega)
/* 153:    */   {
/* 154:226 */     this.bodega = bodega;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public List<Producto> getListaProducto()
/* 158:    */   {
/* 159:235 */     return this.listaProducto;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setListaProducto(List<Producto> listaProducto)
/* 163:    */   {
/* 164:245 */     this.listaProducto = listaProducto;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public List<Bodega> getListaBodega()
/* 168:    */   {
/* 169:254 */     if (this.listaBodega == null) {
/* 170:255 */       this.listaBodega = this.servicioBodega.obtenerListaCombo("nombre", true, null);
/* 171:    */     }
/* 172:257 */     return this.listaBodega;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setListaBodega(List<Bodega> listaBodega)
/* 176:    */   {
/* 177:267 */     this.listaBodega = listaBodega;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public enumUnidad getUnidad()
/* 181:    */   {
/* 182:276 */     return this.unidad;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setUnidad(enumUnidad unidad)
/* 186:    */   {
/* 187:286 */     this.unidad = unidad;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public List<SelectItem> getListaUnidad()
/* 191:    */   {
/* 192:295 */     if (this.listaUnidad == null)
/* 193:    */     {
/* 194:296 */       this.listaUnidad = new ArrayList();
/* 195:297 */       for (enumUnidad unidad : enumUnidad.values())
/* 196:    */       {
/* 197:298 */         SelectItem item = new SelectItem(unidad, unidad.name());
/* 198:299 */         this.listaUnidad.add(item);
/* 199:    */       }
/* 200:    */     }
/* 201:302 */     return this.listaUnidad;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public void setListaUnidad(List<SelectItem> listaUnidad)
/* 205:    */   {
/* 206:312 */     this.listaUnidad = listaUnidad;
/* 207:    */   }
/* 208:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.reportes.controller.ReporteKardexProductoEjemploBean
 * JD-Core Version:    0.7.0.1
 */