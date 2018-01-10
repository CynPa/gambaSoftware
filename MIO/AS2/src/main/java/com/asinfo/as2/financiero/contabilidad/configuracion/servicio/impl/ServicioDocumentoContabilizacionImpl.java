/*   1:    */ package com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.CriterioContabilizacionDao;
/*   4:    */ import com.asinfo.as2.dao.CriterioDistribucionDao;
/*   5:    */ import com.asinfo.as2.dao.DocumentoContabilizacionDao;
/*   6:    */ import com.asinfo.as2.dao.DocumentoVariableProcesoDao;
/*   7:    */ import com.asinfo.as2.entities.ActivoFijo;
/*   8:    */ import com.asinfo.as2.entities.Bodega;
/*   9:    */ import com.asinfo.as2.entities.Canal;
/*  10:    */ import com.asinfo.as2.entities.CategoriaActivo;
/*  11:    */ import com.asinfo.as2.entities.CategoriaEmpresa;
/*  12:    */ import com.asinfo.as2.entities.CategoriaImpuesto;
/*  13:    */ import com.asinfo.as2.entities.CategoriaProducto;
/*  14:    */ import com.asinfo.as2.entities.ConceptoContable;
/*  15:    */ import com.asinfo.as2.entities.CriterioContabilizacion;
/*  16:    */ import com.asinfo.as2.entities.CuentaContable;
/*  17:    */ import com.asinfo.as2.entities.Departamento;
/*  18:    */ import com.asinfo.as2.entities.DestinoCosto;
/*  19:    */ import com.asinfo.as2.entities.Documento;
/*  20:    */ import com.asinfo.as2.entities.DocumentoContabilizacion;
/*  21:    */ import com.asinfo.as2.entities.DocumentoVariableProceso;
/*  22:    */ import com.asinfo.as2.entities.Empleado;
/*  23:    */ import com.asinfo.as2.entities.Empresa;
/*  24:    */ import com.asinfo.as2.entities.Impuesto;
/*  25:    */ import com.asinfo.as2.entities.MotivoAjusteInventario;
/*  26:    */ import com.asinfo.as2.entities.MotivoBajaActivo;
/*  27:    */ import com.asinfo.as2.entities.MotivoNotaCreditoCliente;
/*  28:    */ import com.asinfo.as2.entities.MotivoNotaCreditoProveedor;
/*  29:    */ import com.asinfo.as2.entities.Producto;
/*  30:    */ import com.asinfo.as2.entities.Rubro;
/*  31:    */ import com.asinfo.as2.entities.SubcategoriaActivo;
/*  32:    */ import com.asinfo.as2.entities.SubcategoriaProducto;
/*  33:    */ import com.asinfo.as2.entities.Subempresa;
/*  34:    */ import com.asinfo.as2.entities.Sucursal;
/*  35:    */ import com.asinfo.as2.entities.Zona;
/*  36:    */ import com.asinfo.as2.enumeraciones.DocumentoBase;
/*  37:    */ import com.asinfo.as2.enumeraciones.ProcesoContabilizacionEnum;
/*  38:    */ import com.asinfo.as2.financiero.contabilidad.configuracion.servicio.ServicioDocumentoContabilizacion;
/*  39:    */ import com.asinfo.as2.financiero.excepciones.ExcepcionAS2Financiero;
/*  40:    */ import java.util.HashMap;
/*  41:    */ import java.util.Iterator;
/*  42:    */ import java.util.List;
/*  43:    */ import java.util.Map;
/*  44:    */ import javax.ejb.EJB;
/*  45:    */ import javax.ejb.Stateless;
/*  46:    */ 
/*  47:    */ @Stateless
/*  48:    */ public class ServicioDocumentoContabilizacionImpl
/*  49:    */   implements ServicioDocumentoContabilizacion
/*  50:    */ {
/*  51:    */   @EJB
/*  52:    */   private DocumentoContabilizacionDao documentoContabilizacionDao;
/*  53:    */   @EJB
/*  54:    */   private CriterioContabilizacionDao criterioContabilizacionDao;
/*  55:    */   @EJB
/*  56:    */   private CriterioDistribucionDao criterioDistribucionDao;
/*  57:    */   @EJB
/*  58:    */   private DocumentoVariableProcesoDao documentoVariableProcesoDao;
/*  59:    */   
/*  60:    */   public void guardar(DocumentoContabilizacion documentoContabilizacion)
/*  61:    */     throws ExcepcionAS2Financiero
/*  62:    */   {
/*  63: 60 */     this.documentoContabilizacionDao.guardar(documentoContabilizacion);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void guardarListaDocumentoContabilizacion(List<DocumentoContabilizacion> listaDocumentoContabilizacion)
/*  67:    */     throws ExcepcionAS2Financiero
/*  68:    */   {
/*  69: 73 */     for (DocumentoContabilizacion documentoContabilizacion : listaDocumentoContabilizacion)
/*  70:    */     {
/*  71: 74 */       for (CriterioContabilizacion criterioContabilizacion : documentoContabilizacion.getListaCriterioContabilizacion())
/*  72:    */       {
/*  73: 76 */         if ((!criterioContabilizacion.isEliminado()) && 
/*  74: 77 */           (criterioContabilizacion.getCuentaContable().getNombre() == null)) {
/*  75: 78 */           throw new ExcepcionAS2Financiero("msg_error_cuenta_contable_en_criterio_contabilizacion");
/*  76:    */         }
/*  77: 81 */         this.criterioContabilizacionDao.guardar(criterioContabilizacion);
/*  78:    */       }
/*  79: 83 */       guardar(documentoContabilizacion);
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   public DocumentoContabilizacion buscarPorId(Integer id)
/*  84:    */   {
/*  85: 95 */     return (DocumentoContabilizacion)this.documentoContabilizacionDao.buscarPorId(id);
/*  86:    */   }
/*  87:    */   
/*  88:    */   public List<DocumentoContabilizacion> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  89:    */   {
/*  90:107 */     return this.documentoContabilizacionDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  91:    */   }
/*  92:    */   
/*  93:    */   public List<DocumentoContabilizacion> obtenerListaPorDocumentoBase(int idOrganizacion, DocumentoBase documentoBase)
/*  94:    */   {
/*  95:112 */     return obtenerListaPorDocumentoBase(idOrganizacion, documentoBase, null);
/*  96:    */   }
/*  97:    */   
/*  98:    */   public List<DocumentoContabilizacion> obtenerListaPorDocumentoBase(int idOrganizacion, DocumentoBase documentoBase, ProcesoContabilizacionEnum procesoContabilizacion)
/*  99:    */   {
/* 100:117 */     Map<String, String> filtros = new HashMap();
/* 101:118 */     filtros.put("idOrganizacion", String.valueOf(idOrganizacion));
/* 102:119 */     filtros.put("documentoBase", documentoBase.toString());
/* 103:120 */     if (procesoContabilizacion != null) {
/* 104:121 */       filtros.put("procesoContabilizacion", procesoContabilizacion.toString());
/* 105:    */     }
/* 106:124 */     List<DocumentoContabilizacion> lista = obtenerListaCombo("procesoContabilizacion", true, filtros);
/* 107:125 */     for (Iterator localIterator1 = lista.iterator(); localIterator1.hasNext();)
/* 108:    */     {
/* 109:125 */       documentoContabilizacion = (DocumentoContabilizacion)localIterator1.next();
/* 110:126 */       for (CriterioContabilizacion criterioContabilizacion : documentoContabilizacion.getListaCriterioContabilizacion())
/* 111:    */       {
/* 112:127 */         criterioContabilizacion.getIdCriterioContabilizacion();
/* 113:128 */         criterioContabilizacion.getCuentaContable().getIdCuentaContable();
/* 114:129 */         criterioContabilizacion.getCuentaContable().setCodigoCuentaTransient(criterioContabilizacion.getCuentaContable().getCodigo());
/* 115:130 */         if (criterioContabilizacion.getDocumento() != null) {
/* 116:131 */           criterioContabilizacion.getDocumento().getIdDocumento();
/* 117:    */         }
/* 118:133 */         if (criterioContabilizacion.getSucursal() != null) {
/* 119:134 */           criterioContabilizacion.getSucursal().getIdSucursal();
/* 120:    */         }
/* 121:136 */         if (criterioContabilizacion.getCategoriaEmpresa() != null) {
/* 122:137 */           criterioContabilizacion.getCategoriaEmpresa().getIdCategoriaEmpresa();
/* 123:    */         }
/* 124:139 */         if (criterioContabilizacion.getCliente() != null) {
/* 125:140 */           criterioContabilizacion.getCliente().getIdEmpresa();
/* 126:    */         }
/* 127:142 */         if (criterioContabilizacion.getProveedor() != null) {
/* 128:143 */           criterioContabilizacion.getProveedor().getIdEmpresa();
/* 129:    */         }
/* 130:145 */         if (criterioContabilizacion.getCategoriaProducto() != null) {
/* 131:146 */           criterioContabilizacion.getCategoriaProducto().getIdCategoriaProducto();
/* 132:    */         }
/* 133:148 */         if (criterioContabilizacion.getSubcategoriaProducto() != null) {
/* 134:149 */           criterioContabilizacion.getSubcategoriaProducto().getIdSubcategoriaProducto();
/* 135:    */         }
/* 136:151 */         if (criterioContabilizacion.getProducto() != null) {
/* 137:152 */           criterioContabilizacion.getProducto().getIdProducto();
/* 138:    */         }
/* 139:154 */         if (criterioContabilizacion.getBodega() != null) {
/* 140:155 */           criterioContabilizacion.getBodega().getIdBodega();
/* 141:    */         }
/* 142:157 */         if (criterioContabilizacion.getCanal() != null) {
/* 143:158 */           criterioContabilizacion.getCanal().getIdCanal();
/* 144:    */         }
/* 145:160 */         if (criterioContabilizacion.getSubcliente() != null) {
/* 146:161 */           criterioContabilizacion.getSubcliente().getIdSubempresa();
/* 147:    */         }
/* 148:163 */         if (criterioContabilizacion.getSubproveedor() != null) {
/* 149:164 */           criterioContabilizacion.getSubproveedor().getIdSubempresa();
/* 150:    */         }
/* 151:166 */         if (criterioContabilizacion.getZona() != null) {
/* 152:167 */           criterioContabilizacion.getZona().getIdZona();
/* 153:    */         }
/* 154:169 */         if (criterioContabilizacion.getMotivoNotaCreditoCliente() != null) {
/* 155:170 */           criterioContabilizacion.getMotivoNotaCreditoCliente().getIdMotivoNotaCreditoCliente();
/* 156:    */         }
/* 157:172 */         if (criterioContabilizacion.getMotivoNotaCreditoProveedor() != null) {
/* 158:173 */           criterioContabilizacion.getMotivoNotaCreditoProveedor().getIdMotivoNotaCreditoProveedor();
/* 159:    */         }
/* 160:175 */         if (criterioContabilizacion.getMotivoAjusteInventario() != null) {
/* 161:176 */           criterioContabilizacion.getMotivoAjusteInventario().getIdMotivoAjusteInventario();
/* 162:    */         }
/* 163:178 */         if (criterioContabilizacion.getMotivoBajaActivo() != null) {
/* 164:179 */           criterioContabilizacion.getMotivoBajaActivo().getIdMotivoBajaActivo();
/* 165:    */         }
/* 166:181 */         if (criterioContabilizacion.getCategoriaActivo() != null) {
/* 167:182 */           criterioContabilizacion.getCategoriaActivo().getIdCategoriaActivo();
/* 168:    */         }
/* 169:184 */         if (criterioContabilizacion.getSubcategoriaActivo() != null) {
/* 170:185 */           criterioContabilizacion.getSubcategoriaActivo().getIdSubcategoriaActivo();
/* 171:    */         }
/* 172:187 */         if (criterioContabilizacion.getActivoFijo() != null) {
/* 173:188 */           criterioContabilizacion.getActivoFijo().getIdActivoFijo();
/* 174:    */         }
/* 175:190 */         if (criterioContabilizacion.getEmpleado() != null) {
/* 176:191 */           criterioContabilizacion.getEmpleado().getIdEmpleado();
/* 177:    */         }
/* 178:193 */         if (criterioContabilizacion.getRubro() != null) {
/* 179:194 */           criterioContabilizacion.getRubro().getIdRubro();
/* 180:    */         }
/* 181:196 */         if (criterioContabilizacion.getDepartamento() != null) {
/* 182:197 */           criterioContabilizacion.getDepartamento().getIdDepartamento();
/* 183:    */         }
/* 184:199 */         if (criterioContabilizacion.getCategoriaImpuesto() != null) {
/* 185:200 */           criterioContabilizacion.getCategoriaImpuesto().getIdCategoriaImpuesto();
/* 186:    */         }
/* 187:202 */         if (criterioContabilizacion.getImpuesto() != null) {
/* 188:203 */           criterioContabilizacion.getImpuesto().getIdImpuesto();
/* 189:    */         }
/* 190:205 */         if (criterioContabilizacion.getConceptoContable() != null) {
/* 191:206 */           criterioContabilizacion.getConceptoContable().getIdConceptoContable();
/* 192:    */         }
/* 193:208 */         if (criterioContabilizacion.getDestinoCosto() != null) {
/* 194:209 */           criterioContabilizacion.getDestinoCosto().getIdDestinoCosto();
/* 195:    */         }
/* 196:212 */         this.documentoContabilizacionDao.detach(documentoContabilizacion);
/* 197:    */       }
/* 198:    */     }
/* 199:    */     DocumentoContabilizacion documentoContabilizacion;
/* 200:215 */     return lista;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public List<DocumentoVariableProceso> obtenerListaComboDocumentoVariableProceso(String sortField, boolean sortOrder, Map<String, String> filters)
/* 204:    */   {
/* 205:226 */     return this.documentoVariableProcesoDao.obtenerListaCombo(sortField, sortOrder, filters);
/* 206:    */   }
/* 207:    */   
/* 208:    */   public List<DocumentoContabilizacion> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/* 209:    */   {
/* 210:238 */     return this.documentoContabilizacionDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/* 211:    */   }
/* 212:    */   
/* 213:    */   public DocumentoContabilizacion cargarDetalle(int idDocumentoContabilizacion)
/* 214:    */   {
/* 215:248 */     return this.documentoContabilizacionDao.cargarDetalle(idDocumentoContabilizacion);
/* 216:    */   }
/* 217:    */   
/* 218:    */   public int contarPorCriterio(Map<String, String> filters)
/* 219:    */   {
/* 220:258 */     return this.documentoContabilizacionDao.contarPorCriterio(filters);
/* 221:    */   }
/* 222:    */   
/* 223:    */   public List<CriterioContabilizacion> getListaCriterioContabilizacion(int idOrganizacion, DocumentoBase documentoBase, ProcesoContabilizacionEnum procesoContabilizacion)
/* 224:    */   {
/* 225:266 */     return this.criterioContabilizacionDao.getListaCriterioContabilizacion(idOrganizacion, documentoBase, procesoContabilizacion);
/* 226:    */   }
/* 227:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.financiero.contabilidad.configuracion.servicio.impl.ServicioDocumentoContabilizacionImpl
 * JD-Core Version:    0.7.0.1
 */