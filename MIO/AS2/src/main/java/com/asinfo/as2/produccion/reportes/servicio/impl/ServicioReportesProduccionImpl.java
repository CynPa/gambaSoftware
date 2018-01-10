/*   1:    */ package com.asinfo.as2.produccion.reportes.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.ProductoDao;
/*   4:    */ import com.asinfo.as2.dao.produccion.ReportesProduccionDao;
/*   5:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*   6:    */ import com.asinfo.as2.entities.Producto;
/*   7:    */ import com.asinfo.as2.entities.ProductoMaterial;
/*   8:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*   9:    */ import com.asinfo.as2.entities.Unidad;
/*  10:    */ import com.asinfo.as2.entities.produccion.DetalleOrdenFabricacionMaterial;
/*  11:    */ import com.asinfo.as2.entities.produccion.DetallePlanProduccion;
/*  12:    */ import com.asinfo.as2.entities.produccion.OrdenFabricacion;
/*  13:    */ import com.asinfo.as2.entities.produccion.PlanProduccion;
/*  14:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  15:    */ import com.asinfo.as2.produccion.procesos.servicio.ServicioPlanProduccion;
/*  16:    */ import com.asinfo.as2.produccion.reportes.servicio.ServicioReportesProduccion;
/*  17:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  18:    */ import com.asinfo.as2.utils.NodoArbol;
/*  19:    */ import java.io.Serializable;
/*  20:    */ import java.math.BigDecimal;
/*  21:    */ import java.math.RoundingMode;
/*  22:    */ import java.util.ArrayList;
/*  23:    */ import java.util.Collections;
/*  24:    */ import java.util.Comparator;
/*  25:    */ import java.util.Date;
/*  26:    */ import java.util.HashMap;
/*  27:    */ import java.util.Iterator;
/*  28:    */ import java.util.List;
/*  29:    */ import javax.ejb.EJB;
/*  30:    */ import javax.ejb.Stateless;
/*  31:    */ 
/*  32:    */ @Stateless
/*  33:    */ public class ServicioReportesProduccionImpl
/*  34:    */   implements ServicioReportesProduccion, Serializable
/*  35:    */ {
/*  36:    */   private static final long serialVersionUID = 6825024170922611499L;
/*  37:    */   @EJB
/*  38:    */   private ReportesProduccionDao reportesProduccionDao;
/*  39:    */   @EJB
/*  40:    */   private ServicioPlanProduccion servicioPlanProduccion;
/*  41:    */   @EJB
/*  42:    */   private ServicioProducto servicioProducto;
/*  43:    */   @EJB
/*  44:    */   private ProductoDao productoDao;
/*  45:    */   
/*  46:    */   public List<Object[]> reporteOrdenFabricacion(Date fecha, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto)
/*  47:    */   {
/*  48: 47 */     return this.reportesProduccionDao.reporteOrdenFabricacion(fecha, categoriaProducto, subcategoriaProducto);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public List<Object[]> listaMateriaPrima(PlanProduccion planProduccion, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto)
/*  52:    */   {
/*  53: 52 */     List<Object[]> listaReporteMateriales = new ArrayList();
/*  54: 53 */     planProduccion = this.servicioPlanProduccion.cargarDetalle(planProduccion.getIdPlanProduccion());
/*  55: 54 */     for (DetallePlanProduccion detallePlanProduccion : planProduccion.getListaDetallePlanProduccion())
/*  56:    */     {
/*  57: 55 */       detallePlanProduccion.getProducto().setCantidadProducir(detallePlanProduccion
/*  58: 56 */         .getProducto().getCantidadProduccion().multiply(new BigDecimal(detallePlanProduccion.getBatchLunes())));
/*  59: 57 */       NodoArbol<Producto> productoTerminadoLunes = this.servicioProducto.obtenerArbolComponentes(detallePlanProduccion.getProducto());
/*  60: 58 */       materiasPrimas(productoTerminadoLunes, "1L", listaReporteMateriales, categoriaProducto, subcategoriaProducto);
/*  61:    */       
/*  62: 60 */       detallePlanProduccion.getProducto().setCantidadProducir(detallePlanProduccion
/*  63: 61 */         .getProducto().getCantidadProduccion().multiply(new BigDecimal(detallePlanProduccion.getBatchMartes())));
/*  64: 62 */       NodoArbol<Producto> productoTerminadoMartes = this.servicioProducto.obtenerArbolComponentes(detallePlanProduccion.getProducto());
/*  65: 63 */       materiasPrimas(productoTerminadoMartes, "2M", listaReporteMateriales, categoriaProducto, subcategoriaProducto);
/*  66:    */       
/*  67: 65 */       detallePlanProduccion.getProducto().setCantidadProducir(detallePlanProduccion
/*  68: 66 */         .getProducto().getCantidadProduccion().multiply(new BigDecimal(detallePlanProduccion.getBatchMiercoles())));
/*  69: 67 */       NodoArbol<Producto> productoTerminadoMiercoles = this.servicioProducto.obtenerArbolComponentes(detallePlanProduccion.getProducto());
/*  70: 68 */       materiasPrimas(productoTerminadoMiercoles, "3X", listaReporteMateriales, categoriaProducto, subcategoriaProducto);
/*  71:    */       
/*  72: 70 */       detallePlanProduccion.getProducto().setCantidadProducir(detallePlanProduccion
/*  73: 71 */         .getProducto().getCantidadProduccion().multiply(new BigDecimal(detallePlanProduccion.getBatchJueves())));
/*  74: 72 */       NodoArbol<Producto> productoTerminadoJueves = this.servicioProducto.obtenerArbolComponentes(detallePlanProduccion.getProducto());
/*  75: 73 */       materiasPrimas(productoTerminadoJueves, "4J", listaReporteMateriales, categoriaProducto, subcategoriaProducto);
/*  76:    */       
/*  77: 75 */       detallePlanProduccion.getProducto().setCantidadProducir(detallePlanProduccion
/*  78: 76 */         .getProducto().getCantidadProduccion().multiply(new BigDecimal(detallePlanProduccion.getBatchViernes())));
/*  79: 77 */       NodoArbol<Producto> productoTerminadoViernes = this.servicioProducto.obtenerArbolComponentes(detallePlanProduccion.getProducto());
/*  80: 78 */       materiasPrimas(productoTerminadoViernes, "5V", listaReporteMateriales, categoriaProducto, subcategoriaProducto);
/*  81:    */       
/*  82: 80 */       detallePlanProduccion.getProducto().setCantidadProducir(detallePlanProduccion
/*  83: 81 */         .getProducto().getCantidadProduccion().multiply(new BigDecimal(detallePlanProduccion.getBatchSabado())));
/*  84: 82 */       NodoArbol<Producto> productoTerminadoSabado = this.servicioProducto.obtenerArbolComponentes(detallePlanProduccion.getProducto());
/*  85: 83 */       materiasPrimas(productoTerminadoSabado, "6S", listaReporteMateriales, categoriaProducto, subcategoriaProducto);
/*  86:    */       
/*  87: 85 */       detallePlanProduccion.getProducto().setCantidadProducir(detallePlanProduccion
/*  88: 86 */         .getProducto().getCantidadProduccion().multiply(new BigDecimal(detallePlanProduccion.getBatchDomingo())));
/*  89: 87 */       NodoArbol<Producto> productoTerminadoDomingo = this.servicioProducto.obtenerArbolComponentes(detallePlanProduccion.getProducto());
/*  90: 88 */       materiasPrimas(productoTerminadoDomingo, "7D", listaReporteMateriales, categoriaProducto, subcategoriaProducto);
/*  91:    */     }
/*  92: 92 */     return listaReporteMateriales;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void crearDiasSemanaReporte(NodoArbol<Producto> nap, List<Object[]> listaReporteMateriales, String dia)
/*  96:    */   {
/*  97: 96 */     Object[] materiasPrimas = new Object[5];
/*  98: 97 */     materiasPrimas[0] = ((Producto)nap.getValor()).getCantidadProducir();
/*  99: 98 */     materiasPrimas[1] = ((Producto)nap.getValor()).getCodigo();
/* 100: 99 */     materiasPrimas[2] = ((Producto)nap.getValor()).getNombre();
/* 101:100 */     materiasPrimas[3] = dia;
/* 102:101 */     materiasPrimas[4] = ((Producto)nap.getValor()).getUnidad().getCodigo();
/* 103:102 */     listaReporteMateriales.add(materiasPrimas);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void materiasPrimas(NodoArbol<Producto> productoTerminadoDia, String dia, List<Object[]> listaReporteMateriales, CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto)
/* 107:    */   {
/* 108:107 */     for (NodoArbol<Producto> nap : productoTerminadoDia.getHojas()) {
/* 109:108 */       if (((Producto)nap.getValor()).getCantidadProducir().compareTo(BigDecimal.ZERO) != 0)
/* 110:    */       {
/* 111:109 */         if ((categoriaProducto != null) && (subcategoriaProducto == null) && 
/* 112:110 */           (((Producto)nap.getValor()).getSubcategoriaProducto().getCategoriaProducto().equals(categoriaProducto))) {
/* 113:111 */           crearDiasSemanaReporte(nap, listaReporteMateriales, dia);
/* 114:    */         }
/* 115:113 */         if ((categoriaProducto != null) && (subcategoriaProducto != null) && 
/* 116:114 */           (((Producto)nap.getValor()).getSubcategoriaProducto().equals(subcategoriaProducto))) {
/* 117:115 */           crearDiasSemanaReporte(nap, listaReporteMateriales, dia);
/* 118:    */         }
/* 119:117 */         if ((categoriaProducto == null) && (subcategoriaProducto == null)) {
/* 120:118 */           crearDiasSemanaReporte(nap, listaReporteMateriales, dia);
/* 121:    */         }
/* 122:    */       }
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public List<Object[]> listaRendimientoMateriales(CategoriaProducto categoriaProducto, SubcategoriaProducto subcategoriaProducto, Producto producto, Date fecha, Date fechaHasta, int idOrganizacion)
/* 127:    */   {
/* 128:127 */     List<DetalleOrdenFabricacionMaterial> listaDetalleOrdenFabricacionMateriales = this.reportesProduccionDao.listaMaterialesRendimiento(fecha, fechaHasta, categoriaProducto, subcategoriaProducto, producto, idOrganizacion);
/* 129:    */     
/* 130:129 */     HashMap<String, Object[]> hmDatos = new HashMap();
/* 131:    */     
/* 132:131 */     List<Object[]> listaMateriasPrimas = new ArrayList();
/* 133:133 */     for (Iterator localIterator1 = listaDetalleOrdenFabricacionMateriales.iterator(); localIterator1.hasNext();)
/* 134:    */     {
/* 135:133 */       dofm = (DetalleOrdenFabricacionMaterial)localIterator1.next();
/* 136:134 */       asignarValorProducido(dofm);
/* 137:    */       
/* 138:136 */       List<DetalleOrdenFabricacionMaterial> listaHojasDetalleOrdenFabricacionMaterialPadre = new ArrayList();
/* 139:137 */       List<DetalleOrdenFabricacionMaterial> listaHojasDetalleOrdenFabricacionMaterial = obtenerHojasDetalleOrdenFabricacionMaterial(dofm, listaHojasDetalleOrdenFabricacionMaterialPadre);
/* 140:    */       
/* 141:139 */       productoDofm = this.productoDao.cargarDetalleListaMaterial(dofm.getMaterial());
/* 142:140 */       for (DetalleOrdenFabricacionMaterial auxDofm : listaHojasDetalleOrdenFabricacionMaterial)
/* 143:    */       {
/* 144:141 */         BigDecimal cantidadUtilizadaReal = this.reportesProduccionDao.cantidadUtilizadaReal(auxDofm.getMaterial(), auxDofm.getOrdenFabricacion());
/* 145:142 */         if (cantidadUtilizadaReal.compareTo(BigDecimal.ZERO) != 0)
/* 146:    */         {
/* 147:143 */           auxDofm.setCantidadUtilizadaReal(cantidadUtilizadaReal);
/* 148:144 */           Object[] dato = new Object[9];
/* 149:145 */           dato[0] = dofm.getMaterial().getCodigo();
/* 150:146 */           dato[1] = dofm.getMaterial().getNombre();
/* 151:147 */           dato[2] = FuncionesUtiles.dateToString(FuncionesUtiles.setAtributoFecha(dofm.getOrdenFabricacion().getFechaCierre()));
/* 152:148 */           dato[3] = auxDofm.getMaterial().getCodigo();
/* 153:149 */           dato[4] = auxDofm.getMaterial().getNombre();
/* 154:150 */           dato[5] = auxDofm.getCantidadUtilizadaReal();
/* 155:151 */           dato[6] = dofm.getValorProducido();
/* 156:152 */           dato[7] = dofm.getMaterial().getCantidadProduccion();
/* 157:154 */           for (ProductoMaterial productoMaterial : productoDofm.getListaProductoMaterial()) {
/* 158:155 */             if (productoMaterial.getMaterial().getCodigo().equals(auxDofm.getMaterial().getCodigo()))
/* 159:    */             {
/* 160:156 */               dato[8] = productoMaterial.getCantidad();
/* 161:157 */               break;
/* 162:    */             }
/* 163:    */           }
/* 164:160 */           String productoTerminado = (String)dato[0];
/* 165:161 */           String fechaOrdenFabricacion = (String)dato[2];
/* 166:162 */           String materiaPrima = (String)dato[3];
/* 167:    */           
/* 168:164 */           Object[] datoRecuperado = (Object[])hmDatos.put(productoTerminado + "~" + fechaOrdenFabricacion + "~" + materiaPrima, dato);
/* 169:166 */           if (datoRecuperado != null)
/* 170:    */           {
/* 171:167 */             BigDecimal valorCantidadUtilizadoRecuperado = (BigDecimal)datoRecuperado[5];
/* 172:168 */             BigDecimal valorCantidadUtilizadoDato = (BigDecimal)dato[5];
/* 173:169 */             BigDecimal valorProducidoDatoRecuperado = (BigDecimal)datoRecuperado[6];
/* 174:170 */             BigDecimal valorProducidoDato = (BigDecimal)dato[6];
/* 175:    */             
/* 176:172 */             datoRecuperado[5] = valorCantidadUtilizadoRecuperado.add(valorCantidadUtilizadoDato);
/* 177:173 */             datoRecuperado[6] = valorProducidoDatoRecuperado.add(valorProducidoDato);
/* 178:    */             
/* 179:175 */             String productoTerminadoRecuperado = (String)dato[0];
/* 180:176 */             String fechaOrdenFabricacionRecuperado = (String)dato[2];
/* 181:177 */             String materiaPrimaRecuperado = (String)dato[3];
/* 182:178 */             hmDatos.put(productoTerminadoRecuperado + "~" + fechaOrdenFabricacionRecuperado + "~" + materiaPrimaRecuperado, datoRecuperado);
/* 183:    */           }
/* 184:    */           else
/* 185:    */           {
/* 186:182 */             hmDatos.put(productoTerminado + "~" + fechaOrdenFabricacion + "~" + materiaPrima, dato);
/* 187:    */           }
/* 188:    */         }
/* 189:    */       }
/* 190:    */     }
/* 191:    */     DetalleOrdenFabricacionMaterial dofm;
/* 192:    */     Producto productoDofm;
/* 193:188 */     for (Object[] a : hmDatos.values())
/* 194:    */     {
/* 195:190 */       listaMateriasPrimas.add(a);
/* 196:191 */       Collections.sort(listaMateriasPrimas, new Comparator()
/* 197:    */       {
/* 198:    */         public int compare(Object[] o1, Object[] o2)
/* 199:    */         {
/* 200:194 */           return ((String)o1[0] + "_" + (String)o1[2]).compareTo((String)o2[0] + "_" + (String)o2[2]);
/* 201:    */         }
/* 202:    */       });
/* 203:    */     }
/* 204:199 */     return listaMateriasPrimas;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public void asignarValorProducido(DetalleOrdenFabricacionMaterial detalleOrdenFabricacionMaterial)
/* 208:    */   {
/* 209:203 */     BigDecimal valorProducidoMaterias = BigDecimal.ZERO;
/* 210:204 */     if (detalleOrdenFabricacionMaterial.getDetalleOrdenFabricacionMaterialPadre() != null)
/* 211:    */     {
/* 212:205 */       if (detalleOrdenFabricacionMaterial.getOrdenFabricacion().getCantidadFabricada().compareTo(BigDecimal.ZERO) > 0)
/* 213:    */       {
/* 214:211 */         valorProducidoMaterias = detalleOrdenFabricacionMaterial.getCantidad().multiply(detalleOrdenFabricacionMaterial.getOrdenFabricacion().getCantidadFabricada().divide(detalleOrdenFabricacionMaterial.getOrdenFabricacion().getCantidad(), 6, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
/* 215:212 */         detalleOrdenFabricacionMaterial.setValorProducido(valorProducidoMaterias);
/* 216:    */       }
/* 217:    */     }
/* 218:    */     else {
/* 219:215 */       valorProducidoMaterias = detalleOrdenFabricacionMaterial.getOrdenFabricacion().getCantidadFabricada();
/* 220:    */     }
/* 221:218 */     detalleOrdenFabricacionMaterial.setValorProducido(valorProducidoMaterias);
/* 222:    */   }
/* 223:    */   
/* 224:    */   public List<DetalleOrdenFabricacionMaterial> obtenerHojasDetalleOrdenFabricacionMaterial(DetalleOrdenFabricacionMaterial detalleOrdenFabricacionMaterial, List<DetalleOrdenFabricacionMaterial> lista)
/* 225:    */   {
/* 226:225 */     List<DetalleOrdenFabricacionMaterial> hijosDetalleOrdenFabricacionMaterial = this.reportesProduccionDao.detallesOrdenFabricacionMaterialesHijos(detalleOrdenFabricacionMaterial);
/* 227:227 */     for (DetalleOrdenFabricacionMaterial dofm : hijosDetalleOrdenFabricacionMaterial) {
/* 228:228 */       if (dofm.isIndicadorHoja()) {
/* 229:229 */         lista.add(dofm);
/* 230:    */       } else {
/* 231:231 */         obtenerHojasDetalleOrdenFabricacionMaterial(dofm, lista);
/* 232:    */       }
/* 233:    */     }
/* 234:234 */     return lista;
/* 235:    */   }
/* 236:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.produccion.reportes.servicio.impl.ServicioReportesProduccionImpl
 * JD-Core Version:    0.7.0.1
 */