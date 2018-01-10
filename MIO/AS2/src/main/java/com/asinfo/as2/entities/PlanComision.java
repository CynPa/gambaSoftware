/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.OneToMany;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.Size;
/*  15:    */ 
/*  16:    */ @Entity
/*  17:    */ @Table(name="plan_comision", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  18:    */ public class PlanComision
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="plan_comision", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="plan_comision")
/*  25:    */   @Column(name="id_plan_comision")
/*  26:    */   private int idPlanComision;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @Column(name="codigo", nullable=false, length=10)
/*  32:    */   @Size(min=2, max=10)
/*  33:    */   private String codigo;
/*  34:    */   @Column(name="nombre", nullable=false, length=50)
/*  35:    */   @Size(min=2, max=50)
/*  36:    */   private String nombre;
/*  37:    */   @Column(name="descripcion", nullable=true, length=200)
/*  38:    */   @Size(max=200)
/*  39:    */   private String descripcion;
/*  40:    */   @Column(name="activo", nullable=false)
/*  41:    */   private boolean activo;
/*  42:    */   @Column(name="predeterminado", nullable=false)
/*  43:    */   private boolean predeterminado;
/*  44:    */   @OneToMany(cascade={javax.persistence.CascadeType.DETACH}, fetch=FetchType.LAZY, mappedBy="planComision")
/*  45: 78 */   private List<VersionPlanComision> listaVersionPlanComision = new ArrayList();
/*  46:    */   
/*  47:    */   public int getId()
/*  48:    */   {
/*  49: 98 */     return this.idPlanComision;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int getIdPlanComision()
/*  53:    */   {
/*  54:102 */     return this.idPlanComision;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setIdPlanComision(int idPlanComision)
/*  58:    */   {
/*  59:106 */     this.idPlanComision = idPlanComision;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getIdOrganizacion()
/*  63:    */   {
/*  64:110 */     return this.idOrganizacion;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setIdOrganizacion(int idOrganizacion)
/*  68:    */   {
/*  69:114 */     this.idOrganizacion = idOrganizacion;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getIdSucursal()
/*  73:    */   {
/*  74:118 */     return this.idSucursal;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setIdSucursal(int idSucursal)
/*  78:    */   {
/*  79:122 */     this.idSucursal = idSucursal;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getCodigo()
/*  83:    */   {
/*  84:126 */     return this.codigo;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setCodigo(String codigo)
/*  88:    */   {
/*  89:130 */     this.codigo = codigo;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String getNombre()
/*  93:    */   {
/*  94:134 */     return this.nombre;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setNombre(String nombre)
/*  98:    */   {
/*  99:138 */     this.nombre = nombre;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public String getDescripcion()
/* 103:    */   {
/* 104:142 */     return this.descripcion;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void setDescripcion(String descripcion)
/* 108:    */   {
/* 109:146 */     this.descripcion = descripcion;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean isActivo()
/* 113:    */   {
/* 114:150 */     return this.activo;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setActivo(boolean activo)
/* 118:    */   {
/* 119:154 */     this.activo = activo;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean isPredeterminado()
/* 123:    */   {
/* 124:158 */     return this.predeterminado;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setPredeterminado(boolean predeterminado)
/* 128:    */   {
/* 129:162 */     this.predeterminado = predeterminado;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public List<VersionPlanComision> getListaVersionPlanComision()
/* 133:    */   {
/* 134:166 */     return this.listaVersionPlanComision;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setListaVersionPlanComision(List<VersionPlanComision> listaVersionPlanComision)
/* 138:    */   {
/* 139:170 */     this.listaVersionPlanComision = listaVersionPlanComision;
/* 140:    */   }
/* 141:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.PlanComision
 * JD-Core Version:    0.7.0.1
 */