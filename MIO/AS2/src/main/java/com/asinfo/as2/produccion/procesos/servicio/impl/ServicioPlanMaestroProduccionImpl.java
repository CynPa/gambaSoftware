/*   1:    */ package com.asinfo.as2.produccion.procesos.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.GenericoDao;
/*   4:    */ import com.asinfo.as2.dao.produccion.PlanMaestroProduccionDao;
/*   5:    */ import com.asinfo.as2.dao.produccion.PlanProduccionDao;
/*   6:    */ import com.asinfo.as2.entities.Producto;
/*   7:    */ import com.asinfo.as2.entities.produccion.DetallePlanMaestroProduccion;
/*   8:    */ import com.asinfo.as2.entities.produccion.PlanMaestroProduccion;
/*   9:    */ import com.asinfo.as2.enumeraciones.Mes;
/*  10:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  11:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  12:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioPlanMaestroProduccion;
/*  13:    */ import java.math.BigDecimal;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.Iterator;
/*  17:    */ import java.util.List;
/*  18:    */ import java.util.Map;
/*  19:    */ import java.util.Set;
/*  20:    */ import java.util.TreeSet;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.ejb.Stateless;
/*  23:    */ 
/*  24:    */ @Stateless
/*  25:    */ public class ServicioPlanMaestroProduccionImpl
/*  26:    */   implements ServicioPlanMaestroProduccion
/*  27:    */ {
/*  28:    */   @EJB
/*  29:    */   private PlanMaestroProduccionDao planMaestroProduccionDao;
/*  30:    */   @EJB
/*  31:    */   private GenericoDao<DetallePlanMaestroProduccion> detallePlanMaestroProduccionDao;
/*  32:    */   @EJB
/*  33:    */   private PlanProduccionDao planProduccionDao;
/*  34:    */   
/*  35:    */   public void guardar(PlanMaestroProduccion planMaestroProduccion)
/*  36:    */     throws ExcepcionAS2, AS2Exception
/*  37:    */   {
/*  38: 56 */     validar(planMaestroProduccion);
/*  39:    */     
/*  40: 58 */     this.planMaestroProduccionDao.guardar(planMaestroProduccion);
/*  41: 60 */     for (DetallePlanMaestroProduccion detalle : planMaestroProduccion.getListaDetallePlanMaestroProduccion())
/*  42:    */     {
/*  43: 61 */       if (!detalle.isEliminado()) {
/*  44: 62 */         this.detallePlanMaestroProduccionDao.guardar(detalle);
/*  45:    */       }
/*  46: 65 */       if (detalle.isEliminado()) {
/*  47: 66 */         this.detallePlanMaestroProduccionDao.guardar(detalle);
/*  48:    */       }
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void eliminar(PlanMaestroProduccion planMaestroProduccion)
/*  53:    */   {
/*  54: 73 */     planMaestroProduccion = cargarDetalle(planMaestroProduccion.getId());
/*  55: 74 */     for (DetallePlanMaestroProduccion detalle : planMaestroProduccion.getListaDetallePlanMaestroProduccion()) {
/*  56: 75 */       this.detallePlanMaestroProduccionDao.eliminar(detalle);
/*  57:    */     }
/*  58: 77 */     this.planMaestroProduccionDao.eliminar(planMaestroProduccion);
/*  59:    */   }
/*  60:    */   
/*  61:    */   public PlanMaestroProduccion buscarPorId(int idPlanMaestroProduccion)
/*  62:    */   {
/*  63: 82 */     return (PlanMaestroProduccion)this.planMaestroProduccionDao.buscarPorId(Integer.valueOf(idPlanMaestroProduccion));
/*  64:    */   }
/*  65:    */   
/*  66:    */   public List<PlanMaestroProduccion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  67:    */   {
/*  68: 88 */     return this.planMaestroProduccionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public List<PlanMaestroProduccion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  72:    */   {
/*  73: 93 */     return this.planMaestroProduccionDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public int contarPorCriterio(Map<String, String> filters)
/*  77:    */   {
/*  78: 98 */     return this.planMaestroProduccionDao.contarPorCriterio(filters);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public PlanMaestroProduccion cargarDetalle(int idPlanMaestroProduccion)
/*  82:    */   {
/*  83:103 */     return this.planMaestroProduccionDao.cargarDetalle(idPlanMaestroProduccion);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public List<PlanMaestroProduccion> buscarPlanesMaestrosProduccionPorRangoFecha(int idOrganizacion, Date fechaDesde, Date fechaHasta)
/*  87:    */     throws ExcepcionAS2
/*  88:    */   {
/*  89:109 */     if ((fechaDesde == null) || (fechaHasta == null)) {
/*  90:110 */       throw new ExcepcionAS2("msg_error_fechas_null");
/*  91:    */     }
/*  92:112 */     if (fechaDesde.after(fechaHasta)) {
/*  93:113 */       throw new ExcepcionAS2("msg_error_fecha_hasta");
/*  94:    */     }
/*  95:115 */     return this.planMaestroProduccionDao.buscarPlanesMaestrosProduccionPorRangoFecha(idOrganizacion, fechaDesde, fechaHasta);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public Map<Integer, DetallePlanMaestroProduccion> obtenerDetallesPlanMaestroPorFecha(PlanMaestroProduccion planMaestroProduccion, Mes mes)
/*  99:    */   {
/* 100:121 */     Map<Integer, DetallePlanMaestroProduccion> mapa = new HashMap();
/* 101:122 */     List<DetallePlanMaestroProduccion> lista = this.planMaestroProduccionDao.obtenerDetallesPlanMaestro(planMaestroProduccion, mes);
/* 102:123 */     for (DetallePlanMaestroProduccion detalle : lista) {
/* 103:124 */       mapa.put(Integer.valueOf(detalle.getProducto().getId()), detalle);
/* 104:    */     }
/* 105:126 */     return mapa;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void validar(PlanMaestroProduccion planMaestroProduccion)
/* 109:    */     throws ExcepcionAS2, AS2Exception
/* 110:    */   {
/* 111:130 */     if (planMaestroProduccion.getFechaInicio().after(planMaestroProduccion.getFechaFin())) {
/* 112:131 */       throw new ExcepcionAS2("msg_error_fecha_hasta");
/* 113:    */     }
/* 114:133 */     Map<Integer, Set<String>> mapaProductoMeses = new HashMap();
/* 115:134 */     for (DetallePlanMaestroProduccion detalle : planMaestroProduccion.getListaDetallePlanMaestroProduccion()) {
/* 116:135 */       if (!detalle.isEliminado())
/* 117:    */       {
/* 118:136 */         if (detalle.getProducto().getCantidadProduccion().compareTo(BigDecimal.ZERO) <= 0) {
/* 119:137 */           throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_CANTIDAD_BOM", new String[] { detalle.getProducto().getCodigo(), detalle.getProducto().getNombre() });
/* 120:    */         }
/* 121:141 */         if (detalle.getProporcionLunes().add(detalle.getProporcionMartes()).add(detalle.getProporcionMiercoles()).add(detalle.getProporcionJueves()).add(detalle.getProporcionViernes()).add(detalle.getProporcionSabado()).add(detalle.getProporcionDomingo()).compareTo(new BigDecimal(100)) != 0) {
/* 122:142 */           throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_PROPORCION_DIFERENTE_100", new String[] { detalle.getProducto().getNombre() });
/* 123:    */         }
/* 124:145 */         Set<String> listaMeses = new TreeSet();
/* 125:146 */         for (String mes : detalle.getMesesSeleccionados()) {
/* 126:147 */           listaMeses.add(mes);
/* 127:    */         }
/* 128:149 */         if (mapaProductoMeses.containsKey(Integer.valueOf(detalle.getProducto().getId()))) {
/* 129:150 */           for (??? = listaMeses.iterator(); ((Iterator)???).hasNext();)
/* 130:    */           {
/* 131:150 */             String mes = (String)((Iterator)???).next();
/* 132:151 */             if (!((Set)mapaProductoMeses.get(Integer.valueOf(detalle.getProducto().getId()))).contains(mes)) {
/* 133:152 */               ((Set)mapaProductoMeses.get(Integer.valueOf(detalle.getProducto().getId()))).add(mes);
/* 134:    */             } else {
/* 135:155 */               throw new AS2Exception("com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioProductoImpl.ERROR_DETALLES_IGUALES_MESES", new String[] { detalle.getProducto().getNombre(), Mes.values()[Integer.valueOf(mes).intValue()].getNombre() });
/* 136:    */             }
/* 137:    */           }
/* 138:    */         } else {
/* 139:159 */           mapaProductoMeses.put(Integer.valueOf(detalle.getProducto().getId()), listaMeses);
/* 140:    */         }
/* 141:    */       }
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void actualizarProductos(PlanMaestroProduccion planMaestroProduccion)
/* 146:    */   {
/* 147:168 */     List<Producto> listaProducto = this.planProduccionDao.obtenerProductosPlanificacion(planMaestroProduccion.getIdOrganizacion(), null, 0);
/* 148:170 */     for (DetallePlanMaestroProduccion detalle : planMaestroProduccion.getListaDetallePlanMaestroProduccion())
/* 149:    */     {
/* 150:171 */       boolean encontreDetalle = false;
/* 151:172 */       for (Producto producto : listaProducto) {
/* 152:173 */         if (detalle.getProducto().getIdProducto() == producto.getIdProducto()) {
/* 153:174 */           encontreDetalle = true;
/* 154:    */         }
/* 155:    */       }
/* 156:177 */       if (!encontreDetalle) {
/* 157:178 */         detalle.setEliminado(true);
/* 158:    */       }
/* 159:    */     }
/* 160:182 */     for (Producto producto : listaProducto) {
/* 161:183 */       crearDetallePlanMaestroProduccion(planMaestroProduccion, producto);
/* 162:    */     }
/* 163:    */   }
/* 164:    */   
/* 165:    */   private void crearDetallePlanMaestroProduccion(PlanMaestroProduccion planMaestroProduccion, Producto producto)
/* 166:    */   {
/* 167:189 */     boolean encontreProducto = false;
/* 168:190 */     DetallePlanMaestroProduccion detalleEliminado = null;
/* 169:191 */     for (DetallePlanMaestroProduccion detalle : planMaestroProduccion.getListaDetallePlanMaestroProduccion()) {
/* 170:192 */       if (detalle.getProducto().getIdProducto() == producto.getIdProducto()) {
/* 171:193 */         if (detalle.isEliminado())
/* 172:    */         {
/* 173:194 */           detalleEliminado = detalle;
/* 174:    */         }
/* 175:    */         else
/* 176:    */         {
/* 177:196 */           encontreProducto = true;
/* 178:197 */           break;
/* 179:    */         }
/* 180:    */       }
/* 181:    */     }
/* 182:203 */     if ((!encontreProducto) && (detalleEliminado != null)) {
/* 183:204 */       detalleEliminado.setEliminado(false);
/* 184:    */     }
/* 185:207 */     if ((!encontreProducto) && (detalleEliminado == null))
/* 186:    */     {
/* 187:208 */       DetallePlanMaestroProduccion detalle = new DetallePlanMaestroProduccion();
/* 188:209 */       detalle.setIdOrganizacion(planMaestroProduccion.getIdOrganizacion());
/* 189:210 */       detalle.setIdSucursal(planMaestroProduccion.getIdSucursal());
/* 190:211 */       detalle.setPlanMaestroProduccion(planMaestroProduccion);
/* 191:212 */       detalle.setProducto(producto);
/* 192:    */       
/* 193:214 */       planMaestroProduccion.getListaDetallePlanMaestroProduccion().add(detalle);
/* 194:    */     }
/* 195:    */   }
/* 196:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.procesos.servicio.impl.ServicioPlanMaestroProduccionImpl
 * JD-Core Version:    0.7.0.1
 */