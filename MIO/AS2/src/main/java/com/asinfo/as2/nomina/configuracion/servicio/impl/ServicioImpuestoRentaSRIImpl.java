/*   1:    */ package com.asinfo.as2.nomina.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.ImpuestoRentaSRIDao;
/*   4:    */ import com.asinfo.as2.entities.ImpuestoRentaSRI;
/*   5:    */ import com.asinfo.as2.entities.Organizacion;
/*   6:    */ import com.asinfo.as2.entities.Sucursal;
/*   7:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   8:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*   9:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioImpuestoRentaSRI;
/*  10:    */ import com.asinfo.as2.nomina.excepciones.ExcepcionAS2Nomina;
/*  11:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*  14:    */ import java.io.IOException;
/*  15:    */ import java.io.InputStream;
/*  16:    */ import java.io.PrintStream;
/*  17:    */ import java.math.BigDecimal;
/*  18:    */ import java.util.HashMap;
/*  19:    */ import java.util.List;
/*  20:    */ import java.util.Map;
/*  21:    */ import javax.ejb.EJB;
/*  22:    */ import javax.ejb.SessionContext;
/*  23:    */ import javax.ejb.Stateless;
/*  24:    */ import org.apache.log4j.Logger;
/*  25:    */ import org.apache.poi.hssf.usermodel.HSSFCell;
/*  26:    */ 
/*  27:    */ @Stateless
/*  28:    */ public class ServicioImpuestoRentaSRIImpl
/*  29:    */   extends AbstractServicioAS2
/*  30:    */   implements ServicioImpuestoRentaSRI
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = 1L;
/*  33:    */   @EJB
/*  34:    */   private ImpuestoRentaSRIDao impuestoRentaSRIDao;
/*  35:    */   
/*  36:    */   public void guardar(ImpuestoRentaSRI impuestoRentaSRI)
/*  37:    */     throws ExcepcionAS2Nomina, AS2Exception, ExcepcionAS2
/*  38:    */   {
/*  39:    */     try
/*  40:    */     {
/*  41: 67 */       validar(impuestoRentaSRI);
/*  42: 68 */       this.impuestoRentaSRIDao.guardar(impuestoRentaSRI);
/*  43:    */     }
/*  44:    */     catch (AS2Exception e)
/*  45:    */     {
/*  46: 70 */       this.context.setRollbackOnly();
/*  47: 71 */       throw e;
/*  48:    */     }
/*  49:    */     catch (Exception e)
/*  50:    */     {
/*  51: 73 */       this.context.setRollbackOnly();
/*  52: 74 */       throw new ExcepcionAS2Nomina(e);
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void validar(ImpuestoRentaSRI impuestoRentaSRI)
/*  57:    */     throws AS2Exception
/*  58:    */   {
/*  59: 80 */     List<ImpuestoRentaSRI> listaImpuestoRentaSRI = this.impuestoRentaSRIDao.verificarCantidades(impuestoRentaSRI.getIdOrganizacion(), impuestoRentaSRI
/*  60: 81 */       .getAnio());
/*  61: 82 */     System.out.println(impuestoRentaSRI.isEditado());
/*  62: 83 */     for (ImpuestoRentaSRI irSRI : listaImpuestoRentaSRI) {
/*  63: 84 */       if (!impuestoRentaSRI.isEditado())
/*  64:    */       {
/*  65: 85 */         if (((impuestoRentaSRI.getDesde().compareTo(irSRI.getDesde()) == 1) && (impuestoRentaSRI.getDesde().compareTo(irSRI.getHasta()) == -1)) || (
/*  66: 86 */           (impuestoRentaSRI.getHasta().compareTo(irSRI.getDesde()) == 1) && 
/*  67: 87 */           (impuestoRentaSRI.getHasta().compareTo(irSRI.getHasta()) == -1))) {
/*  68: 88 */           throw new AS2Exception("com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioImpuestoRentaSRIImpl.ERROR_RANGOS_IMPUESTO_RENTASRI", new String[] { "" });
/*  69:    */         }
/*  70:    */       }
/*  71: 91 */       else if ((impuestoRentaSRI.getDesde().compareTo(irSRI.getDesde()) == 1) && (impuestoRentaSRI.getDesde().compareTo(irSRI.getHasta()) == -1)) {
/*  72: 92 */         throw new AS2Exception("com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioImpuestoRentaSRIImpl.ERROR_RANGOS_IMPUESTO_RENTASRI", new String[] { "" });
/*  73:    */       }
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void eliminar(ImpuestoRentaSRI impuestoRentaSRI)
/*  78:    */   {
/*  79:105 */     this.impuestoRentaSRIDao.eliminar(impuestoRentaSRI);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public ImpuestoRentaSRI buscarPorId(int idImpuestoRentaSRI)
/*  83:    */   {
/*  84:115 */     return (ImpuestoRentaSRI)this.impuestoRentaSRIDao.buscarPorId(Integer.valueOf(idImpuestoRentaSRI));
/*  85:    */   }
/*  86:    */   
/*  87:    */   public List<ImpuestoRentaSRI> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  88:    */   {
/*  89:127 */     return this.impuestoRentaSRIDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public List<ImpuestoRentaSRI> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  93:    */   {
/*  94:137 */     return this.impuestoRentaSRIDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public int contarPorCriterio(Map<String, String> filters)
/*  98:    */   {
/*  99:147 */     return this.impuestoRentaSRIDao.contarPorCriterio(filters);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public ImpuestoRentaSRI cargarDetalle(int idImpuestoRentaSRI)
/* 103:    */   {
/* 104:157 */     return null;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public List<ImpuestoRentaSRI> obtenerTablaPorAnio(int anio, int idOrganizacion)
/* 108:    */   {
/* 109:167 */     return this.impuestoRentaSRIDao.obtenerTablaPorAnio(anio, idOrganizacion);
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void cargarImpuestoRentaSRI(int idOrganizacion, InputStream imInputStream, int filaInicial)
/* 113:    */     throws ExcepcionAS2, IOException
/* 114:    */   {
/* 115:173 */     HashMap<String, String> filters = new HashMap();
/* 116:174 */     filters.put("idOrganizacion", Integer.toString(AppUtil.getOrganizacion().getId()));
/* 117:    */     
/* 118:176 */     int filaActual = filaInicial;
/* 119:177 */     int columnaErronea = -1;
/* 120:178 */     HSSFCell[] filaErronea = new HSSFCell[0];
/* 121:    */     try
/* 122:    */     {
/* 123:183 */       HSSFCell[][] datos = FuncionesUtiles.leerExcelFinal(imInputStream, filaInicial, 0);
/* 124:185 */       for (HSSFCell[] fila : datos)
/* 125:    */       {
/* 126:187 */         filaErronea = fila;
/* 127:188 */         filaActual++;
/* 128:    */         
/* 129:    */ 
/* 130:191 */         BigDecimal desde = new BigDecimal(fila[(columnaErronea = 0)].getNumericCellValue());
/* 131:192 */         BigDecimal hasta = new BigDecimal(fila[(columnaErronea = 1)].getNumericCellValue());
/* 132:193 */         BigDecimal fraccionBasica = new BigDecimal(fila[(columnaErronea = 2)].getNumericCellValue());
/* 133:194 */         BigDecimal porcentaje = new BigDecimal(fila[(columnaErronea = 3)].getNumericCellValue());
/* 134:    */         
/* 135:196 */         ImpuestoRentaSRI impuestoRentaSRI = new ImpuestoRentaSRI();
/* 136:197 */         impuestoRentaSRI.setAnio(FuncionesUtiles.obtenerAnioActual());
/* 137:198 */         impuestoRentaSRI.setActivo(true);
/* 138:199 */         impuestoRentaSRI.setDesde(desde);
/* 139:200 */         impuestoRentaSRI.setHasta(hasta);
/* 140:201 */         impuestoRentaSRI.setFraccionBasica(fraccionBasica);
/* 141:202 */         impuestoRentaSRI.setPorcentaje(porcentaje);
/* 142:203 */         impuestoRentaSRI.setIdSucursal(AppUtil.getSucursal().getId());
/* 143:204 */         impuestoRentaSRI.setIdOrganizacion(AppUtil.getOrganizacion().getId());
/* 144:    */         
/* 145:206 */         this.impuestoRentaSRIDao.guardar(impuestoRentaSRI);
/* 146:    */       }
/* 147:    */     }
/* 148:    */     catch (IllegalArgumentException e)
/* 149:    */     {
/* 150:210 */       LOG.info("Error al migrar impuesto renta SRI", e);
/* 151:211 */       this.context.setRollbackOnly();
/* 152:    */       
/* 153:213 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaErronea + 1) + " Dato: " + filaErronea[columnaErronea].toString());
/* 154:    */     }
/* 155:    */     catch (IllegalStateException e)
/* 156:    */     {
/* 157:216 */       LOG.info("Error al migrar impuesto renta SRI", e);
/* 158:217 */       this.context.setRollbackOnly();
/* 159:    */       
/* 160:219 */       throw new ExcepcionAS2("msg_error_formato_incorrecto", "Fila: " + filaActual + " Columna: " + (columnaErronea + 1) + " Dato: " + filaErronea[columnaErronea].toString());
/* 161:    */     }
/* 162:    */     catch (Exception e)
/* 163:    */     {
/* 164:222 */       LOG.error("Error al migrar impuesto renta SRI", e);
/* 165:223 */       this.context.setRollbackOnly();
/* 166:224 */       throw new ExcepcionAS2("msg_error_cargar_datos", e);
/* 167:    */     }
/* 168:    */   }
/* 169:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioImpuestoRentaSRIImpl
 * JD-Core Version:    0.7.0.1
 */