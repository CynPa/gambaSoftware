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
/*  17:    */ @Table(name="categoria_rubro", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  18:    */ public class CategoriaRubro
/*  19:    */   extends EntidadBase
/*  20:    */ {
/*  21:    */   private static final long serialVersionUID = 1L;
/*  22:    */   @Id
/*  23:    */   @TableGenerator(name="categoria_rubro", initialValue=0, allocationSize=50)
/*  24:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="categoria_rubro")
/*  25:    */   @Column(name="id_categoria_rubro")
/*  26:    */   private int idCategoriaRubro;
/*  27:    */   @Column(name="id_organizacion", nullable=false)
/*  28:    */   private int idOrganizacion;
/*  29:    */   @Column(name="id_sucursal", nullable=false)
/*  30:    */   private int idSucursal;
/*  31:    */   @Column(name="codigo", nullable=false)
/*  32:    */   @Size(min=2, max=10)
/*  33:    */   private String codigo;
/*  34:    */   @Column(name="nombre", nullable=false, length=50)
/*  35:    */   @Size(min=2, max=50)
/*  36:    */   private String nombre;
/*  37:    */   @Column(name="descripcion", nullable=true)
/*  38:    */   @Size(max=200)
/*  39:    */   private String descripcion;
/*  40:    */   @Column(name="activo", nullable=false)
/*  41:    */   private boolean activo;
/*  42:    */   @Column(name="predeterminado", nullable=false)
/*  43:    */   private boolean predeterminado;
/*  44:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="categoriaRubro")
/*  45: 73 */   private List<CategoriaRubroRubro> listaCategoriaRubroRubro = new ArrayList();
/*  46:    */   
/*  47:    */   public CategoriaRubro() {}
/*  48:    */   
/*  49:    */   public CategoriaRubro(int idCategoriaRubro, String codigo, String nombre)
/*  50:    */   {
/*  51: 88 */     this.idCategoriaRubro = idCategoriaRubro;
/*  52: 89 */     this.nombre = nombre;
/*  53: 90 */     this.codigo = codigo;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int getId()
/*  57:    */   {
/*  58:105 */     return this.idCategoriaRubro;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public int getIdCategoriaRubro()
/*  62:    */   {
/*  63:116 */     return this.idCategoriaRubro;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setIdCategoriaRubro(int idCategoriaRubro)
/*  67:    */   {
/*  68:127 */     this.idCategoriaRubro = idCategoriaRubro;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getIdOrganizacion()
/*  72:    */   {
/*  73:136 */     return this.idOrganizacion;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setIdOrganizacion(int idOrganizacion)
/*  77:    */   {
/*  78:146 */     this.idOrganizacion = idOrganizacion;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public int getIdSucursal()
/*  82:    */   {
/*  83:155 */     return this.idSucursal;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setIdSucursal(int idSucursal)
/*  87:    */   {
/*  88:165 */     this.idSucursal = idSucursal;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String getNombre()
/*  92:    */   {
/*  93:174 */     return this.nombre;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setNombre(String nombre)
/*  97:    */   {
/*  98:184 */     this.nombre = nombre;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public String getCodigo()
/* 102:    */   {
/* 103:193 */     return this.codigo;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setCodigo(String codigo)
/* 107:    */   {
/* 108:203 */     this.codigo = codigo;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String getDescripcion()
/* 112:    */   {
/* 113:212 */     return this.descripcion;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setDescripcion(String descripcion)
/* 117:    */   {
/* 118:222 */     this.descripcion = descripcion;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean isActivo()
/* 122:    */   {
/* 123:231 */     return this.activo;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setActivo(boolean activo)
/* 127:    */   {
/* 128:241 */     this.activo = activo;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean isPredeterminado()
/* 132:    */   {
/* 133:250 */     return this.predeterminado;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setPredeterminado(boolean predeterminado)
/* 137:    */   {
/* 138:260 */     this.predeterminado = predeterminado;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public List<CategoriaRubroRubro> getListaCategoriaRubroRubro()
/* 142:    */   {
/* 143:264 */     return this.listaCategoriaRubroRubro;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setListaCategoriaRubroRubro(List<CategoriaRubroRubro> listaCategoriaRubroRubro)
/* 147:    */   {
/* 148:269 */     this.listaCategoriaRubroRubro = listaCategoriaRubroRubro;
/* 149:    */   }
/* 150:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CategoriaRubro
 * JD-Core Version:    0.7.0.1
 */