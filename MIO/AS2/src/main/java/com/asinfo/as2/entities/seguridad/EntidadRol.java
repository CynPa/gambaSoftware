/*   1:    */ package com.asinfo.as2.entities.seguridad;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.ManyToMany;
/*  14:    */ import javax.persistence.OneToMany;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.persistence.Transient;
/*  18:    */ import javax.validation.constraints.NotNull;
/*  19:    */ import javax.validation.constraints.Size;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="rol", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "nombre"})})
/*  23:    */ public class EntidadRol
/*  24:    */   extends EntidadBase
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="rol", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="rol")
/*  30:    */   @Column(name="id_rol", unique=true, nullable=false)
/*  31:    */   private int idRol;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal", nullable=false)
/*  35:    */   private int idSucursal;
/*  36:    */   @Column(name="nombre", nullable=false, length=50)
/*  37:    */   @NotNull
/*  38:    */   @Size(min=2, max=50)
/*  39:    */   private String nombre;
/*  40:    */   @Column(name="descripcion", nullable=true, length=200)
/*  41:    */   @NotNull
/*  42:    */   @Size(max=200)
/*  43:    */   private String descripcion;
/*  44:    */   @Column(name="activo", nullable=false)
/*  45:    */   private boolean activo;
/*  46:    */   @Transient
/*  47:    */   private boolean traAsignado;
/*  48:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="entidadRol")
/*  49:    */   private Collection<EntidadPermiso> listaPermiso;
/*  50:    */   @ManyToMany(mappedBy="listaRol")
/*  51:    */   private List<EntidadUsuario> listaUsuario;
/*  52:    */   
/*  53:    */   public int getId()
/*  54:    */   {
/*  55: 88 */     return this.idRol;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int getIdRol()
/*  59:    */   {
/*  60: 97 */     return this.idRol;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setIdRol(int idRol)
/*  64:    */   {
/*  65:107 */     this.idRol = idRol;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdOrganizacion()
/*  69:    */   {
/*  70:116 */     return this.idOrganizacion;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdOrganizacion(int idOrganizacion)
/*  74:    */   {
/*  75:126 */     this.idOrganizacion = idOrganizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIdSucursal()
/*  79:    */   {
/*  80:135 */     return this.idSucursal;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdSucursal(int idSucursal)
/*  84:    */   {
/*  85:145 */     this.idSucursal = idSucursal;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public String getNombre()
/*  89:    */   {
/*  90:154 */     return this.nombre;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setNombre(String nombre)
/*  94:    */   {
/*  95:164 */     this.nombre = nombre;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getDescripcion()
/*  99:    */   {
/* 100:173 */     return this.descripcion;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setDescripcion(String descripcion)
/* 104:    */   {
/* 105:183 */     this.descripcion = descripcion;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Collection<EntidadPermiso> getListaPermiso()
/* 109:    */   {
/* 110:192 */     if (this.listaPermiso == null) {
/* 111:193 */       this.listaPermiso = new ArrayList();
/* 112:    */     }
/* 113:195 */     return this.listaPermiso;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setListaPermiso(Collection<EntidadPermiso> listaPermiso)
/* 117:    */   {
/* 118:205 */     this.listaPermiso = listaPermiso;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public boolean isActivo()
/* 122:    */   {
/* 123:214 */     return this.activo;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setActivo(boolean activo)
/* 127:    */   {
/* 128:224 */     this.activo = activo;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean isTraAsignado()
/* 132:    */   {
/* 133:233 */     return this.traAsignado;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setTraAsignado(boolean traAsignado)
/* 137:    */   {
/* 138:243 */     this.traAsignado = traAsignado;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public List<EntidadUsuario> getListaUsuario()
/* 142:    */   {
/* 143:250 */     return this.listaUsuario;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void setListaUsuario(List<EntidadUsuario> listaUsuario)
/* 147:    */   {
/* 148:257 */     this.listaUsuario = listaUsuario;
/* 149:    */   }
/* 150:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.seguridad.EntidadRol
 * JD-Core Version:    0.7.0.1
 */