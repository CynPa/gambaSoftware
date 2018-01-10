/*   1:    */ package com.asinfo.as2.entities.nomina.asistencia;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.Empleado;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.OneToMany;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.validation.constraints.Size;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="grupo_trabajo")
/*  20:    */ public class GrupoTrabajo
/*  21:    */   extends EntidadBase
/*  22:    */ {
/*  23:    */   private static final long serialVersionUID = 1L;
/*  24:    */   @Id
/*  25:    */   @TableGenerator(name="grupo_trabajo", initialValue=0, allocationSize=50)
/*  26:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="grupo_trabajo")
/*  27:    */   @Column(name="id_grupo_trabajo")
/*  28:    */   private int idGrupoTrabajo;
/*  29:    */   @Column(name="id_organizacion", nullable=false)
/*  30:    */   private int idOrganizacion;
/*  31:    */   @Column(name="id_sucursal", nullable=false)
/*  32:    */   private int idSucursal;
/*  33:    */   @Column(name="codigo", nullable=false)
/*  34:    */   @Size(min=2, max=10)
/*  35:    */   private String codigo;
/*  36:    */   @Column(name="nombre", nullable=false, length=50)
/*  37:    */   @Size(min=2, max=50)
/*  38:    */   private String nombre;
/*  39:    */   @Column(name="descripcion", nullable=false)
/*  40:    */   @Size(max=200)
/*  41:    */   private String descripcion;
/*  42:    */   @Column(name="activo", nullable=false)
/*  43:    */   private boolean activo;
/*  44:    */   @Column(name="predeterminado", nullable=false)
/*  45:    */   private boolean predeterminado;
/*  46:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="grupoTrabajo")
/*  47: 56 */   private List<Empleado> listaEmpleados = new ArrayList();
/*  48:    */   
/*  49:    */   public int getId()
/*  50:    */   {
/*  51: 62 */     return this.idGrupoTrabajo;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int getIdGrupoTrabajo()
/*  55:    */   {
/*  56: 66 */     return this.idGrupoTrabajo;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setIdGrupoTrabajo(int idGrupoTrabajo)
/*  60:    */   {
/*  61: 70 */     this.idGrupoTrabajo = idGrupoTrabajo;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getIdOrganizacion()
/*  65:    */   {
/*  66: 74 */     return this.idOrganizacion;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setIdOrganizacion(int idOrganizacion)
/*  70:    */   {
/*  71: 78 */     this.idOrganizacion = idOrganizacion;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public int getIdSucursal()
/*  75:    */   {
/*  76: 82 */     return this.idSucursal;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void setIdSucursal(int idSucursal)
/*  80:    */   {
/*  81: 86 */     this.idSucursal = idSucursal;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public List<Empleado> getListaEmpleados()
/*  85:    */   {
/*  86: 90 */     return this.listaEmpleados;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void setListaEmpleados(List<Empleado> listaEmpleados)
/*  90:    */   {
/*  91: 94 */     this.listaEmpleados = listaEmpleados;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getCodigo()
/*  95:    */   {
/*  96: 98 */     return this.codigo;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void setCodigo(String codigo)
/* 100:    */   {
/* 101:102 */     this.codigo = codigo;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public String getNombre()
/* 105:    */   {
/* 106:106 */     return this.nombre;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public void setNombre(String nombre)
/* 110:    */   {
/* 111:110 */     this.nombre = nombre;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String getDescripcion()
/* 115:    */   {
/* 116:114 */     return this.descripcion;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setDescripcion(String descripcion)
/* 120:    */   {
/* 121:118 */     this.descripcion = descripcion;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public boolean isActivo()
/* 125:    */   {
/* 126:122 */     return this.activo;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void setActivo(boolean activo)
/* 130:    */   {
/* 131:126 */     this.activo = activo;
/* 132:    */   }
/* 133:    */   
/* 134:    */   public boolean isPredeterminado()
/* 135:    */   {
/* 136:130 */     return this.predeterminado;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setPredeterminado(boolean predeterminado)
/* 140:    */   {
/* 141:134 */     this.predeterminado = predeterminado;
/* 142:    */   }
/* 143:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.nomina.asistencia.GrupoTrabajo
 * JD-Core Version:    0.7.0.1
 */