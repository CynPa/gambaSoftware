/*   1:    */ package com.asinfo.as2.nomina.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.DetalleDocumentoDigitalizadoDao;
/*   4:    */ import com.asinfo.as2.dao.DocumentoDigitalizadoDepartamentoDao;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.entities.CategoriaDocumentoDigitalizado;
/*   7:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*   8:    */ import com.asinfo.as2.entities.DetalleDocumentoDigitalizado;
/*   9:    */ import com.asinfo.as2.entities.DocumentoDigitalizado;
/*  10:    */ import com.asinfo.as2.entities.DocumentoDigitalizadoCategoriaEmpresa;
/*  11:    */ import com.asinfo.as2.entities.DocumentoDigitalizadoDepartamento;
/*  12:    */ import com.asinfo.as2.entities.Empleado;
/*  13:    */ import com.asinfo.as2.entities.Empresa;
/*  14:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioDetalleDocumentoDigitalizado;
/*  15:    */ import java.util.ArrayList;
/*  16:    */ import java.util.Collections;
/*  17:    */ import java.util.Date;
/*  18:    */ import java.util.HashMap;
/*  19:    */ import java.util.Iterator;
/*  20:    */ import java.util.List;
/*  21:    */ import java.util.Map;
/*  22:    */ import javax.ejb.EJB;
/*  23:    */ import javax.ejb.Stateless;
/*  24:    */ 
/*  25:    */ @Stateless
/*  26:    */ public class ServicioDetalleDocumentoDigitalizadoImpl
/*  27:    */   implements ServicioDetalleDocumentoDigitalizado
/*  28:    */ {
/*  29:    */   @EJB
/*  30:    */   DetalleDocumentoDigitalizadoDao detalleDocumentoDigitalizadoDao;
/*  31:    */   @EJB
/*  32:    */   DocumentoDigitalizadoDepartamentoDao documentoDigitalizadoDepartamentoDao;
/*  33:    */   @EJB
/*  34:    */   private ServicioEmpresa servicioEmpresa;
/*  35:    */   
/*  36:    */   public List<DetalleDocumentoDigitalizado> cargarListaDetalleDocumentoDigitalizadoEmpleado(int idOrganizacion, int idEmpleado, int idDepartamento)
/*  37:    */   {
/*  38: 40 */     List<DetalleDocumentoDigitalizado> listaArchivados = this.detalleDocumentoDigitalizadoDao.cargarListaDetalleDocumentoDigitalizadoEmpleado(idEmpleado);
/*  39:    */     
/*  40: 42 */     List<DocumentoDigitalizadoDepartamento> listaDocumentoDigitalizadoDepartamento = this.documentoDigitalizadoDepartamentoDao.cargarDocumentosPorDepartamento(idDepartamento, idEmpleado);
/*  41:    */     
/*  42: 44 */     List<DetalleDocumentoDigitalizado> listaCompleta = listaArchivados;
/*  43: 45 */     Empleado empleado = new Empleado();
/*  44: 46 */     empleado.setId(idEmpleado);
/*  45: 47 */     empleado.setIdEmpleado(idEmpleado);
/*  46: 48 */     for (Iterator localIterator = listaDocumentoDigitalizadoDepartamento.iterator(); localIterator.hasNext();)
/*  47:    */     {
/*  48: 48 */       documentoDigitalizadoDepartamento = (DocumentoDigitalizadoDepartamento)localIterator.next();
/*  49: 49 */       DetalleDocumentoDigitalizado detalleDocumentoDigitalizado = new DetalleDocumentoDigitalizado();
/*  50: 50 */       detalleDocumentoDigitalizado.setDocumentoDigitalizadoDepartamento(documentoDigitalizadoDepartamento);
/*  51: 51 */       detalleDocumentoDigitalizado.setEmpleado(empleado);
/*  52: 52 */       listaCompleta.add(detalleDocumentoDigitalizado);
/*  53:    */     }
/*  54:    */     DocumentoDigitalizadoDepartamento documentoDigitalizadoDepartamento;
/*  55: 55 */     Collections.sort(listaCompleta);
/*  56:    */     
/*  57: 57 */     int idCategoria = 0;
/*  58: 58 */     for (DetalleDocumentoDigitalizado detalleDocumentoDigitalizado : listaCompleta) {
/*  59: 60 */       if (detalleDocumentoDigitalizado.getDocumentoDigitalizadoDepartamento().getDocumentoDigitalizado().getCategoriaDocumentoDigitalizado().getIdCategoriaDocumentoDigitalizado() != idCategoria)
/*  60:    */       {
/*  61: 61 */         detalleDocumentoDigitalizado.setPrimero(true);
/*  62:    */         
/*  63: 63 */         idCategoria = detalleDocumentoDigitalizado.getDocumentoDigitalizadoDepartamento().getDocumentoDigitalizado().getCategoriaDocumentoDigitalizado().getIdCategoriaDocumentoDigitalizado();
/*  64:    */       }
/*  65:    */     }
/*  66: 67 */     return listaCompleta;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public List<DetalleDocumentoDigitalizado> cargarListaDetalleDocumentoDigitalizadoEmpresa(int idOrganizacion, int idEmpresa, int idCategoriaEmpresa, boolean indicadorProveedor, boolean indicadorCliente, int idDocumentoDigitalizado)
/*  70:    */   {
/*  71: 73 */     List<DetalleDocumentoDigitalizado> listaArchivados = this.detalleDocumentoDigitalizadoDao.cargarListaDetalleDocumentoDigitalizadoEmpresa(idEmpresa, idOrganizacion, idDocumentoDigitalizado);
/*  72:    */     
/*  73:    */ 
/*  74:    */ 
/*  75: 77 */     List<DocumentoDigitalizadoCategoriaEmpresa> listaDetalleDocumentoDigitalizadoClienteProveedor = this.detalleDocumentoDigitalizadoDao.cargarListaDetalleDocumentoDigitalizadoClienteProveedor(idCategoriaEmpresa, indicadorCliente, indicadorProveedor, idDocumentoDigitalizado);
/*  76:    */     
/*  77:    */ 
/*  78: 80 */     Map<String, DetalleDocumentoDigitalizado> hmDetalleDocumentoDigitalizado = new HashMap();
/*  79: 82 */     for (DetalleDocumentoDigitalizado dd : listaArchivados)
/*  80:    */     {
/*  81: 83 */       dd.getDocumentoDigitalizado().getCategoriaDocumentoDigitalizado().getNombre();
/*  82: 84 */       hmDetalleDocumentoDigitalizado.put(dd.getDocumentoDigitalizado().getNombre() + "~" + dd
/*  83: 85 */         .getDocumentoDigitalizado().getCategoriaDocumentoDigitalizado().getNombre(), dd);
/*  84:    */     }
/*  85: 88 */     List<DetalleDocumentoDigitalizado> listaCompleta = listaArchivados;
/*  86:    */     
/*  87: 90 */     Empresa empresa = new Empresa();
/*  88: 91 */     empresa.setId(idEmpresa);
/*  89: 92 */     empresa.setIdEmpresa(idEmpresa);
/*  90: 94 */     for (DocumentoDigitalizadoCategoriaEmpresa dd : listaDetalleDocumentoDigitalizadoClienteProveedor) {
/*  91: 95 */       if (hmDetalleDocumentoDigitalizado.get(dd.getDocumentoDigitalizado().getNombre() + "~" + dd
/*  92: 96 */         .getDocumentoDigitalizado().getCategoriaDocumentoDigitalizado().getNombre()) == null)
/*  93:    */       {
/*  94: 97 */         DetalleDocumentoDigitalizado ddde = new DetalleDocumentoDigitalizado();
/*  95: 98 */         ddde.setIdOrganizacion(idOrganizacion);
/*  96: 99 */         ddde.setDocumentoDigitalizado(dd.getDocumentoDigitalizado());
/*  97:100 */         ddde.setEmpresa(empresa);
/*  98:101 */         ddde.setDocumentoDigitalizadoCategoriaEmpresa(dd);
/*  99:102 */         hmDetalleDocumentoDigitalizado.put(dd.getDocumentoDigitalizado().getNombre() + "~" + dd
/* 100:103 */           .getDocumentoDigitalizado().getCategoriaDocumentoDigitalizado().getNombre(), ddde);
/* 101:104 */         listaCompleta.add(ddde);
/* 102:    */       }
/* 103:    */     }
/* 104:108 */     return listaCompleta;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public List<CategoriaDocumentoDigitalizado> cargarListaCategoriaDocumentoDigitalizadoEmpleado(int idOrganizacion, int idEmpleado, int idDepartamento)
/* 108:    */   {
/* 109:114 */     List<DetalleDocumentoDigitalizado> listaArchivados = this.detalleDocumentoDigitalizadoDao.cargarListaDetalleDocumentoDigitalizadoEmpleado(idEmpleado);
/* 110:    */     
/* 111:116 */     List<DocumentoDigitalizadoDepartamento> listaDocumentoDigitalizadoDepartamento = this.documentoDigitalizadoDepartamentoDao.cargarDocumentosPorDepartamento(idDepartamento, idEmpleado);
/* 112:    */     
/* 113:118 */     List<DetalleDocumentoDigitalizado> listaCompleta = listaArchivados;
/* 114:119 */     Empleado empleado = new Empleado();
/* 115:120 */     empleado.setId(idEmpleado);
/* 116:121 */     empleado.setIdEmpleado(idEmpleado);
/* 117:122 */     for (Iterator localIterator1 = listaDocumentoDigitalizadoDepartamento.iterator(); localIterator1.hasNext();)
/* 118:    */     {
/* 119:122 */       documentoDigitalizadoDepartamento = (DocumentoDigitalizadoDepartamento)localIterator1.next();
/* 120:123 */       DetalleDocumentoDigitalizado detalleDocumentoDigitalizado = new DetalleDocumentoDigitalizado();
/* 121:124 */       detalleDocumentoDigitalizado.setDocumentoDigitalizadoDepartamento(documentoDigitalizadoDepartamento);
/* 122:125 */       detalleDocumentoDigitalizado.setEmpleado(empleado);
/* 123:126 */       listaCompleta.add(detalleDocumentoDigitalizado);
/* 124:    */     }
/* 125:    */     DocumentoDigitalizadoDepartamento documentoDigitalizadoDepartamento;
/* 126:129 */     Object categorias = new ArrayList();
/* 127:130 */     for (DetalleDocumentoDigitalizado detalle : listaCompleta)
/* 128:    */     {
/* 129:131 */       CategoriaDocumentoDigitalizado categoria = null;
/* 130:132 */       for (CategoriaDocumentoDigitalizado categoriaDocumentoDigitalizado : (List)categorias) {
/* 131:134 */         if (detalle.getDocumentoDigitalizadoDepartamento().getDocumentoDigitalizado().getCategoriaDocumentoDigitalizado().getId() == categoriaDocumentoDigitalizado.getId()) {
/* 132:135 */           categoria = categoriaDocumentoDigitalizado;
/* 133:    */         }
/* 134:    */       }
/* 135:138 */       if (categoria != null)
/* 136:    */       {
/* 137:139 */         categoria.getListaDetalleDocumentoDigitalizadoEmpleado().add(detalle);
/* 138:    */       }
/* 139:    */       else
/* 140:    */       {
/* 141:141 */         categoria = detalle.getDocumentoDigitalizadoDepartamento().getDocumentoDigitalizado().getCategoriaDocumentoDigitalizado();
/* 142:142 */         categoria.getListaDetalleDocumentoDigitalizadoEmpleado().add(detalle);
/* 143:143 */         ((List)categorias).add(categoria);
/* 144:    */       }
/* 145:    */     }
/* 146:148 */     return categorias;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void guardar(DetalleDocumentoDigitalizado detalleDocumentoDigitalizado)
/* 150:    */   {
/* 151:153 */     this.detalleDocumentoDigitalizadoDao.guardar(detalleDocumentoDigitalizado);
/* 152:    */   }
/* 153:    */   
/* 154:    */   public List reporteDocumentosDigitalizadosPorEmpleado(int idOrganizacion, int idEmpleado, int idDepartamento)
/* 155:    */   {
/* 156:159 */     List lista = new ArrayList();
/* 157:160 */     List<DetalleDocumentoDigitalizado> listaDetalles = cargarListaDetalleDocumentoDigitalizadoEmpleado(idOrganizacion, idEmpleado, idDepartamento);
/* 158:161 */     for (DetalleDocumentoDigitalizado detalleDocumentoDigitalizado : listaDetalles)
/* 159:    */     {
/* 160:162 */       Object[] objetos = new Object[6];
/* 161:163 */       objetos[0] = detalleDocumentoDigitalizado.getDocumentoDigitalizadoDepartamento().getDocumentoDigitalizado()
/* 162:164 */         .getCategoriaDocumentoDigitalizado().getNombre();
/* 163:165 */       objetos[1] = detalleDocumentoDigitalizado.getDocumentoDigitalizadoDepartamento().getDocumentoDigitalizado().getNombre();
/* 164:166 */       objetos[2] = detalleDocumentoDigitalizado.getFechaDesde();
/* 165:167 */       objetos[3] = detalleDocumentoDigitalizado.getFechaHasta();
/* 166:168 */       objetos[4] = Boolean.valueOf(detalleDocumentoDigitalizado.getDocumentoDigitalizadoDepartamento().isRequerido());
/* 167:169 */       objetos[5] = Boolean.valueOf(detalleDocumentoDigitalizado.getId() == 0 ? 0 : true);
/* 168:170 */       lista.add(objetos);
/* 169:    */     }
/* 170:173 */     return lista;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public List reporteDocumentosDigitalizados(int idOrganizacion, int idEmpleado, int idDepartamento, int idDocumentoDigitalizado, boolean cargados, boolean noCargados, Date fechaVencer)
/* 174:    */   {
/* 175:180 */     List<Object[]> lista = this.detalleDocumentoDigitalizadoDao.cargarListaDetalleDocumentoDigitalizado(idOrganizacion, idEmpleado, idDepartamento, idDocumentoDigitalizado, cargados, noCargados);
/* 176:    */     
/* 177:182 */     List<Object[]> listaResult = new ArrayList();
/* 178:183 */     for (Object[] object : lista)
/* 179:    */     {
/* 180:184 */       Object[] objetos = new Object[9];
/* 181:185 */       objetos[0] = object[0];
/* 182:186 */       objetos[1] = object[1];
/* 183:187 */       objetos[2] = object[2];
/* 184:188 */       objetos[3] = object[3];
/* 185:189 */       objetos[4] = object[4];
/* 186:190 */       objetos[5] = object[5];
/* 187:191 */       List<DetalleDocumentoDigitalizado> listaDetalles = new ArrayList();
/* 188:    */       
/* 189:193 */       listaDetalles = this.detalleDocumentoDigitalizadoDao.reporteDocumentosCargarDetalle(((Integer)object[6]).intValue(), ((Integer)object[7]).intValue(), fechaVencer);
/* 190:194 */       if (cargados) {
/* 191:195 */         for (DetalleDocumentoDigitalizado detalleDocumentoDigitalizado : listaDetalles)
/* 192:    */         {
/* 193:196 */           objetos[6] = Boolean.valueOf(true);
/* 194:197 */           objetos[7] = detalleDocumentoDigitalizado.getFechaDesde();
/* 195:198 */           objetos[8] = detalleDocumentoDigitalizado.getFechaHasta();
/* 196:199 */           listaResult.add(objetos);
/* 197:    */         }
/* 198:    */       }
/* 199:202 */       if ((noCargados) && (listaDetalles.size() == 0) && (fechaVencer == null))
/* 200:    */       {
/* 201:203 */         objetos[6] = Boolean.valueOf(false);
/* 202:204 */         objetos[7] = null;
/* 203:205 */         objetos[8] = null;
/* 204:206 */         listaResult.add(objetos);
/* 205:    */       }
/* 206:    */     }
/* 207:210 */     return listaResult;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public List<Object[]> reporteDocumentosDigitalizadosEmpresa(int idOrganizacion, Empresa empresa, CategoriaEmpresa categoriaEmpresa, boolean indicadorCliente, boolean indicadorProveedor, DocumentoDigitalizado documentoDigitalizado, boolean cargados, boolean noCargados, Date fechaVencer)
/* 211:    */   {
/* 212:218 */     return this.detalleDocumentoDigitalizadoDao.getReporteDocumentoDigitalizadoEmpresa(idOrganizacion, categoriaEmpresa, empresa, documentoDigitalizado, fechaVencer, indicadorCliente, indicadorProveedor, cargados, noCargados);
/* 213:    */   }
/* 214:    */   
/* 215:    */   private Object[] crearDetalleReporte(DetalleDocumentoDigitalizado detalleDocumentoDigitalizado, Empresa empresa)
/* 216:    */   {
/* 217:223 */     Object[] row = new Object[9];
/* 218:224 */     row[0] = detalleDocumentoDigitalizado.getDocumentoDigitalizado().getCategoriaDocumentoDigitalizado().getNombre();
/* 219:225 */     row[1] = detalleDocumentoDigitalizado.getDocumentoDigitalizado().getNombre();
/* 220:226 */     row[3] = empresa.getNombreFiscal();
/* 221:227 */     row[4] = empresa.getIdentificacion();
/* 222:228 */     row[5] = empresa.getCategoriaEmpresa().getNombre();
/* 223:229 */     row[6] = detalleDocumentoDigitalizado.getFechaDesde();
/* 224:230 */     row[7] = detalleDocumentoDigitalizado.getFechaHasta();
/* 225:231 */     row[8] = detalleDocumentoDigitalizado.getFichero();
/* 226:232 */     return row;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public List<DetalleDocumentoDigitalizado> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 230:    */   {
/* 231:238 */     return this.detalleDocumentoDigitalizadoDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 232:    */   }
/* 233:    */   
/* 234:    */   public List<DetalleDocumentoDigitalizado> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/* 235:    */   {
/* 236:243 */     return this.detalleDocumentoDigitalizadoDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 237:    */   }
/* 238:    */   
/* 239:    */   public int contarPorCriterio(Map<String, String> filters)
/* 240:    */   {
/* 241:248 */     return this.detalleDocumentoDigitalizadoDao.contarPorCriterio(filters);
/* 242:    */   }
/* 243:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.servicio.impl.ServicioDetalleDocumentoDigitalizadoImpl
 * JD-Core Version:    0.7.0.1
 */