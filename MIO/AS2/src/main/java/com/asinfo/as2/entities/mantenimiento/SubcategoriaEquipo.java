/*   1:    */ package com.asinfo.as2.entities.mantenimiento;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.io.Serializable;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.Table;
/*  14:    */ import javax.persistence.TableGenerator;
/*  15:    */ import javax.validation.constraints.NotNull;
/*  16:    */ import javax.validation.constraints.Size;
/*  17:    */ 
/*  18:    */ @Entity
/*  19:    */ @Table(name="subcategoria_equipo", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  20:    */ public class SubcategoriaEquipo
/*  21:    */   extends EntidadBase
/*  22:    */   implements Serializable
/*  23:    */ {
/*  24:    */   private static final long serialVersionUID = 1L;
/*  25:    */   @Id
/*  26:    */   @TableGenerator(name="subcategoria_equipo", initialValue=0, allocationSize=50)
/*  27:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="subcategoria_equipo")
/*  28:    */   @Column(name="id_subcategoria_equipo")
/*  29:    */   private int idSubcategoriaEquipo;
/*  30:    */   @Column(name="id_organizacion")
/*  31:    */   private int idOrganizacion;
/*  32:    */   @Column(name="id_sucursal")
/*  33:    */   private int idSucursal;
/*  34:    */   @Column(name="codigo", nullable=false, length=20)
/*  35:    */   @NotNull
/*  36:    */   @Size(min=1, max=20)
/*  37:    */   private String codigo;
/*  38:    */   @Column(name="nombre", nullable=false, length=100)
/*  39:    */   @NotNull
/*  40:    */   @Size(min=2, max=100)
/*  41:    */   private String nombre;
/*  42:    */   @Column(name="descripcion", length=200, nullable=true)
/*  43:    */   @Size(max=200)
/*  44:    */   private String descripcion;
/*  45:    */   @Column(name="predeterminado", nullable=false)
/*  46:    */   private boolean predeterminado;
/*  47:    */   @Column(name="activo", nullable=false)
/*  48:    */   private boolean activo;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_categoria_equipo", nullable=false)
/*  51:    */   private CategoriaEquipo categoriaEquipo;
/*  52:    */   
/*  53:    */   public int getId()
/*  54:    */   {
/*  55: 83 */     return this.idSubcategoriaEquipo;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getIdSubcategoriaEquipo()
/*  59:    */   {
/*  60: 90 */     return this.idSubcategoriaEquipo;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setIdSubcategoriaEquipo(int idSubcategoriaEquipo)
/*  64:    */   {
/*  65: 98 */     this.idSubcategoriaEquipo = idSubcategoriaEquipo;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdOrganizacion()
/*  69:    */   {
/*  70:105 */     return this.idOrganizacion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdOrganizacion(int idOrganizacion)
/*  74:    */   {
/*  75:113 */     this.idOrganizacion = idOrganizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIdSucursal()
/*  79:    */   {
/*  80:120 */     return this.idSucursal;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdSucursal(int idSucursal)
/*  84:    */   {
/*  85:128 */     this.idSucursal = idSucursal;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String getCodigo()
/*  89:    */   {
/*  90:135 */     return this.codigo;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setCodigo(String codigo)
/*  94:    */   {
/*  95:143 */     this.codigo = codigo;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getNombre()
/*  99:    */   {
/* 100:150 */     return this.nombre;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setNombre(String nombre)
/* 104:    */   {
/* 105:158 */     this.nombre = nombre;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getDescripcion()
/* 109:    */   {
/* 110:165 */     return this.descripcion;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setDescripcion(String descripcion)
/* 114:    */   {
/* 115:173 */     this.descripcion = descripcion;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public boolean isPredeterminado()
/* 119:    */   {
/* 120:180 */     return this.predeterminado;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setPredeterminado(boolean predeterminado)
/* 124:    */   {
/* 125:188 */     this.predeterminado = predeterminado;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isActivo()
/* 129:    */   {
/* 130:195 */     return this.activo;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setActivo(boolean activo)
/* 134:    */   {
/* 135:203 */     this.activo = activo;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public CategoriaEquipo getCategoriaEquipo()
/* 139:    */   {
/* 140:210 */     return this.categoriaEquipo;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setCategoriaEquipo(CategoriaEquipo categoriaEquipo)
/* 144:    */   {
/* 145:218 */     this.categoriaEquipo = categoriaEquipo;
/* 146:    */   }
/* 147:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.mantenimiento.SubcategoriaEquipo
 * JD-Core Version:    0.7.0.1
 */