/*   1:    */ package com.asinfo.as2.entities.seguridad;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.EntidadBase;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.GeneratedValue;
/*   8:    */ import javax.persistence.GenerationType;
/*   9:    */ import javax.persistence.Id;
/*  10:    */ import javax.persistence.ManyToMany;
/*  11:    */ import javax.persistence.Table;
/*  12:    */ import javax.persistence.TableGenerator;
/*  13:    */ import javax.persistence.Transient;
/*  14:    */ 
/*  15:    */ @Entity
/*  16:    */ @Table(name="accion")
/*  17:    */ public class EntidadAccion
/*  18:    */   extends EntidadBase
/*  19:    */ {
/*  20:    */   private static final long serialVersionUID = 1L;
/*  21:    */   @Id
/*  22:    */   @TableGenerator(name="accion", initialValue=0, allocationSize=50)
/*  23:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="accion")
/*  24:    */   @Column(name="id_accion", unique=true, nullable=false)
/*  25:    */   private int idAccion;
/*  26:    */   @Column(name="id_organizacion", nullable=false)
/*  27:    */   private int idOrganizacion;
/*  28:    */   @Column(name="nombre", nullable=false, length=50)
/*  29:    */   private String nombre;
/*  30:    */   @Column(name="mascara", nullable=false)
/*  31:    */   private int mascara;
/*  32:    */   @ManyToMany(mappedBy="listaAccion")
/*  33:    */   private List<EntidadPermiso> listaPermiso;
/*  34:    */   @Transient
/*  35:    */   private boolean asignarTodos;
/*  36:    */   
/*  37:    */   public int getId()
/*  38:    */   {
/*  39: 75 */     return this.idAccion;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public String getNombre()
/*  43:    */   {
/*  44: 84 */     return this.nombre;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void setNombre(String nombre)
/*  48:    */   {
/*  49: 94 */     this.nombre = nombre;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public int getMascara()
/*  53:    */   {
/*  54:103 */     return this.mascara;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void setMascara(int mascara)
/*  58:    */   {
/*  59:113 */     this.mascara = mascara;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int getIdAccion()
/*  63:    */   {
/*  64:122 */     return this.idAccion;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setIdAccion(int idAccion)
/*  68:    */   {
/*  69:132 */     this.idAccion = idAccion;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public int getIdOrganizacion()
/*  73:    */   {
/*  74:139 */     return this.idOrganizacion;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setIdOrganizacion(int idOrganizacion)
/*  78:    */   {
/*  79:147 */     this.idOrganizacion = idOrganizacion;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public List<EntidadPermiso> getListaPermiso()
/*  83:    */   {
/*  84:154 */     return this.listaPermiso;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setListaPermiso(List<EntidadPermiso> listaPermiso)
/*  88:    */   {
/*  89:161 */     this.listaPermiso = listaPermiso;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public boolean isAsignarTodos()
/*  93:    */   {
/*  94:165 */     return this.asignarTodos;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public void setAsignarTodos(boolean asignarTodos)
/*  98:    */   {
/*  99:169 */     this.asignarTodos = asignarTodos;
/* 100:    */   }
/* 101:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.seguridad.EntidadAccion
 * JD-Core Version:    0.7.0.1
 */