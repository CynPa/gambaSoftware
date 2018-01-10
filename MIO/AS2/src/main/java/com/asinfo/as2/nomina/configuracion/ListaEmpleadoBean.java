/*   1:    */ package com.asinfo.as2.nomina.configuracion;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   4:    */ import com.asinfo.as2.entities.Empleado;
/*   5:    */ import com.asinfo.as2.nomina.configuracion.servicio.ServicioEmpleado;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import javax.annotation.PostConstruct;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.faces.bean.ManagedBean;
/*  12:    */ import javax.faces.bean.ViewScoped;
/*  13:    */ import javax.faces.model.SelectItem;
/*  14:    */ import org.primefaces.component.datatable.DataTable;
/*  15:    */ import org.primefaces.model.LazyDataModel;
/*  16:    */ import org.primefaces.model.SortOrder;
/*  17:    */ 
/*  18:    */ @ManagedBean
/*  19:    */ @ViewScoped
/*  20:    */ public class ListaEmpleadoBean
/*  21:    */   extends PageControllerAS2
/*  22:    */ {
/*  23:    */   @EJB
/*  24:    */   private ServicioEmpleado servicioEmpleado;
/*  25:    */   private LazyDataModel<Empleado> listaEmpleado;
/*  26:    */   private SelectItem[] listaActivoItem;
/*  27:    */   private DataTable dtEmpleado;
/*  28:    */   
/*  29:    */   @PostConstruct
/*  30:    */   public void init()
/*  31:    */   {
/*  32: 65 */     this.listaEmpleado = new LazyDataModel()
/*  33:    */     {
/*  34:    */       private static final long serialVersionUID = 1L;
/*  35:    */       
/*  36:    */       public List<Empleado> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  37:    */       {
/*  38: 72 */         if (!filters.containsKey("activo")) {
/*  39: 74 */           filters.put("activo", String.valueOf(true));
/*  40:    */         }
/*  41: 77 */         List<Empleado> lista = new ArrayList();
/*  42: 78 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  43:    */         
/*  44: 80 */         lista = ListaEmpleadoBean.this.servicioEmpleado.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  45:    */         
/*  46: 82 */         ListaEmpleadoBean.this.listaEmpleado.setRowCount(ListaEmpleadoBean.this.servicioEmpleado.contarPorCriterio(filters));
/*  47:    */         
/*  48: 84 */         return lista;
/*  49:    */       }
/*  50:    */     };
/*  51:    */   }
/*  52:    */   
/*  53:    */   public LazyDataModel<Empleado> getListaEmpleado()
/*  54:    */   {
/*  55:101 */     return this.listaEmpleado;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void setListaEmpleado(LazyDataModel<Empleado> listaEmpleado)
/*  59:    */   {
/*  60:111 */     this.listaEmpleado = listaEmpleado;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public DataTable getDtEmpleado()
/*  64:    */   {
/*  65:120 */     return this.dtEmpleado;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setDtEmpleado(DataTable dtEmpleado)
/*  69:    */   {
/*  70:130 */     this.dtEmpleado = dtEmpleado;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public String editar()
/*  74:    */   {
/*  75:140 */     return null;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String guardar()
/*  79:    */   {
/*  80:150 */     return null;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String eliminar()
/*  84:    */   {
/*  85:160 */     return null;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String limpiar()
/*  89:    */   {
/*  90:170 */     return null;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String cargarDatos()
/*  94:    */   {
/*  95:180 */     return null;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public SelectItem[] getListaActivoItem()
/*  99:    */   {
/* 100:184 */     if (this.listaActivoItem == null)
/* 101:    */     {
/* 102:185 */       List<SelectItem> lista = new ArrayList();
/* 103:186 */       lista.add(new SelectItem("", ""));
/* 104:187 */       lista.add(new SelectItem("false", "false"));
/* 105:188 */       lista.add(new SelectItem("true", "true"));
/* 106:189 */       this.listaActivoItem = ((SelectItem[])lista.toArray(new SelectItem[lista.size()]));
/* 107:    */     }
/* 108:191 */     return this.listaActivoItem;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setListaActivoItem(SelectItem[] listaActivoItem)
/* 112:    */   {
/* 113:195 */     this.listaActivoItem = listaActivoItem;
/* 114:    */   }
/* 115:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.nomina.configuracion.ListaEmpleadoBean
 * JD-Core Version:    0.7.0.1
 */